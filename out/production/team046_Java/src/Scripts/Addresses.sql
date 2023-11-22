CREATE TABLE Addresses (
	house_number integer,
    city_name integer,
    street_name text,
    postcode varchar(8),
    userID integer,
    PRIMARY KEY (house_number, postcode),
    FOREIGN KEY (userID) REFERENCES Accounts(userID)
);