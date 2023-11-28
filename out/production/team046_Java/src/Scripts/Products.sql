 CREATE TABLE Products (
	product_code varchar(6),
    brand_name varchar(255),
    product_name varchar(255),
    price double(7,2),
    gauge_type varchar(2),
    quantity integer,
    dcc_code varchar(255),
    is_digital boolean,
    PRIMARY KEY (product_code)
);

CREATE TABLE Eras (
	product_code varchar(6),
    era_code varchar(2),
    PRIMARY KEY (product_code),
    FOREIGN KEY (product_code) REFERENCES Products(product_code)
);

CREATE TABLE Packs (
	product_code varchar(6),
    component_code varchar(6),
    quantity integer,
    PRIMARY KEY (product_code),
    FOREIGN KEY (product_code) REFERENCES Products(product_code),
    FOREIGN KEY (component_code) REFERENCES Products(product_code)
);