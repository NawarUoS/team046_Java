CREATE TABLE Addresses (
	house_number integer,
    city_name integer,
    street_name text,
    postcode varchar(8),
    userID varchar(60),
    PRIMARY KEY (house_number, postcode),
    FOREIGN KEY (userID) REFERENCES Accounts(userID)
);