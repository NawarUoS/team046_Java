CREATE TABLE Accounts (
	userID varchar(60) NOT NULL,
    forename text(30) NOT NULL,
    surname text(30) NOT NULL,
    email_address text(254) NOT NULL,
    password text NOT NULL,
    PRIMARY KEY (userID)
);
    