CREATE TABLE OrderLines (
PRIMARY KEY (orderLineNumber),
orderLineNumber INT NOT NULL,
items_quantity INT NOT NULL,
product_code VARCHAR (6) NOT NULL,
order_number INT NOT NULL,
FOREIGN KEY (product_code) REFERENCES Products(product_code),
FOREIGN KEY (order_number) REFERENCES Orders(order_number)
);