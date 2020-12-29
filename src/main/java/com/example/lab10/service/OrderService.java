package com.example.lab10.service;

import com.example.lab10.dto.OrderDto;
import com.example.lab10.exception.NoOrderFoundException;
import com.example.lab10.exception.NoProductFoundException;
import com.example.lab10.exception.NoStockAvailableException;
import com.example.lab10.mapper.OrderMapper;
import com.example.lab10.model.Order;
import com.example.lab10.model.OrderItem;
import com.example.lab10.repository.ItemRepository;
import com.example.lab10.repository.OrderRepository;
import com.example.lab10.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.lab10.model.Status.ACTIVE;
import static com.example.lab10.model.Status.CANCELLED;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, ItemRepository itemRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDto save(List<OrderItem> items) {
        List<OrderItem> itemsToBeOrdered = items.stream()
                .map(
                        itemOrdered -> {
                            OrderItem item = new OrderItem();
                            var p = productRepository.getProductByName(itemOrdered.getProduct().getName());
                            if (!p.isEmpty()) {
                                item.setProduct(p.get());
                                item.setQuantity(itemOrdered.getQuantity());
                                if (itemOrdered.getQuantity() > item.getProduct().getAvailableStock()) {
                                    throw new NoStockAvailableException();
                                }
                            } else {
                                throw new NoProductFoundException();
                            }
                            return item;
                        }
                ).collect(Collectors.toList());

        if (items.size() != itemsToBeOrdered.size()) {
            throw new NoProductFoundException();
        }

        Order order = new Order();
        order.setStatus(ACTIVE);

        Double totalPrice = itemsToBeOrdered.stream()
                .map(item -> item.getProduct().getPrice() * item.getQuantity())
                .reduce(0.0, Double::sum);
        order.setTotalPrice(totalPrice);

        long orderId = orderRepository.save(order);

        itemsToBeOrdered.forEach(
                item -> {
                    productRepository.decrementStock(item.getProduct(), item.getQuantity());
                    itemRepository.save(item, (int) orderId);
                }
        );

        order.setId((int) orderId);
        return orderMapper.toDto(order, itemsToBeOrdered);
    }

    public OrderDto cancelOrder(Integer orderId){
        Optional<Order> optionalOrder = orderRepository.getOrderById(orderId);
        if(optionalOrder.isPresent()){
            Order order = new Order();
            order.setId(orderId);
            order.setStatus(CANCELLED);
            orderRepository.updateOrderStatus(order);
            return orderMapper.toDto(order, new ArrayList<>());
        }else{
            throw new NoOrderFoundException();
        }
    }
}
