CREATE TABLE Accounts (
	userID varchar(60) NOT NULL,
    forename text(30) NOT NULL,
    surname text(30) NOT NULL,
    email_address text(254) NOT NULL,
    unique_password_hash text(254) NOT NULL,
    user_customer tinyint(1) NOT NULL,
    user_staff tinyint(1) NOT NULL,
    user_manager tinyint(1) NOT NULL,
    PRIMARY KEY (userID)
);
    