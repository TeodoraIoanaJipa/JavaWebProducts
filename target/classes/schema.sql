CREATE database IF NOT EXISTS lab10;

CREATE TABLE IF NOT EXISTS lab10.`orders` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `total_price` DOUBLE NOT NULL,
    `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS lab10.`products` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `price` DOUBLE NOT NULL,
    `available_stock` INT NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS lab10.`order_items` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `productid` INT NOT NULL,
    `orderid` INT NOT NULL,
    `quantity` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_order_idx` (`orderid` ASC) VISIBLE,
    INDEX `FK_product_idx` (`productid` ASC) VISIBLE,
    CONSTRAINT `FK_order`
    FOREIGN KEY (`orderid`)
    REFERENCES `lab10`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `FK_product`
    FOREIGN KEY (`productid`)
    REFERENCES `lab10`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
