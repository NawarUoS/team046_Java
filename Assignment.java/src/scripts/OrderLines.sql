CREATE TABLE OrderLines (
    order_line_number INT NOT NULL,
    items_quantity INT NOT NULL,
    order_line_cost DOUBLE NOT NULL,
    product_code VARCHAR(6) NOT NULL,
    order_number INT NOT NULL,
    PRIMARY KEY (order_line_number, order_number),
    FOREIGN KEY (product_code) REFERENCES Products(product_code),
    FOREIGN KEY (order_number) REFERENCES Orders(order_number)
);
