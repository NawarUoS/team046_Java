 CREATE TABLE Products (
	product_code varchar(6),
    brand_name text,
    product_name text,
    gauge_type varchar(2),
    model_scale integer,
    quantity integer,
    PRIMARY KEY (product_code)
);

CREATE TABLE Locomotives (
	product_code varchar(6),
    dcc_code text,
    PRIMARY KEY (product_code),
    FOREIGN KEY (product_code) REFERENCES Products(product_code)
);

CREATE TABLE Track (
	product_code varchar(6),
    PRIMARY KEY (product_code),
    FOREIGN KEY (product_code) REFERENCES Products(product_code)
);

CREATE TABLE Controller (
	product_code varchar(6),
    is_digital boolean,
    PRIMARY KEY (product_code),
    FOREIGN KEY (product_code) REFERENCES Products(product_code)
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
    PRIMARY KEY (product_code),
    FOREIGN KEY (product_code) REFERENCES Products(product_code),
    FOREIGN KEY (component_code) REFERENCES Products(product_code)
);
