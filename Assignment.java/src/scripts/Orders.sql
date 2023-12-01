CREATE TABLE Orders (
PRIMARY KEY (order_number),
order_number INT NOT NULL,
order_date DATE NOT NULL,
total_cost DECIMAL NOT NULL,
order_status CHAR (1) NOT NULL,
userID varchar(60),
FOREIGN KEY (userID) REFERENCES Accounts(userID)
);