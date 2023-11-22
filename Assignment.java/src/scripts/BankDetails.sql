CREATE TABLE BankDetails (
	card_number integer,
    card_name text,
    expiry_date text,
    security_code integer,
    userID integer NOT NULL,
    PRIMARY KEY (card_number),
    FOREIGN KEY (userID) REFERENCES Accounts(userID)
);