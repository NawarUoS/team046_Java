CREATE TABLE Accounts (
	userID integer NOT NULL,
    forename text(30) NOT NULL,
    surname text(30) NOT NULL,
    email_address text(254) NOT NULL,
    password text NOT NULL,
    PRIMARY KEY (userID)
);
    