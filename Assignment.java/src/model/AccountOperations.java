package src.model;

import src.account.*;
import src.util.HashedPasswordGenerator;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountOperations {
    // Get account details by userID
    public Account getAccountByID(Connection connection, String userID)
                                                                throws Error {
        try {
            // Query the database to fetch user information
            String sql = "SELECT forename, surname, email_address, " +
                    "unique_password_hash, " +
                    "user_customer, user_staff, user_manager FROM Accounts " +
                    "WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String forename = resultSet.getString("forename");
                String surname = resultSet.getString("surname");
                String emailAddress =
                        resultSet.getString("email_address");
                String passwordHash =
                    resultSet.getString("unique_password_hash");
                String userCustomer =
                        resultSet.getString("user_customer");
                String userStaff =
                        resultSet.getString("user_staff");
                String userManager =
                        resultSet.getString("user_manager");

                return new Account(userID, combineUserRoles(userCustomer,
                        userStaff, userManager), emailAddress, passwordHash,
                        forename, surname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("User not found.");
    }

    public Account getAccountByEmail(Connection connection, String emailAddress)
            throws Error {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userID, forename, surname, " +
                    "unique_password_hash, " +
                    "user_customer, user_staff, user_manager FROM Accounts " +
                    "WHERE email_address = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userID =
                        resultSet.getString("userID");
                String forename = resultSet.getString("forename");
                String surname = resultSet.getString("surname");
                String passwordHash =
                        resultSet.getString("unique_password_hash");
                String userCustomer =
                        resultSet.getString("user_customer");
                String userStaff =
                        resultSet.getString("user_staff");
                String userManager =
                        resultSet.getString("user_manager");

                return new Account(userID, combineUserRoles(userCustomer,
                        userStaff, userManager), emailAddress, passwordHash,
                        forename, surname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("User not found.");
    }

    // Combine user roles into List
    public List<UserRole> combineUserRoles(String userCustomer,
                                        String userStaff, String userManager) {
        List<UserRole> userRoles = new ArrayList<>();

        if (Integer.parseInt(userCustomer) == 1)
            userRoles.add(UserRole.CUSTOMER);
        if (Integer.parseInt(userStaff) == 1)
            userRoles.add(UserRole.STAFF);
        if (Integer.parseInt(userManager) == 1)
            userRoles.add(UserRole.MANAGER);

        return userRoles;
    };

    // Save account into database
    public String saveAccountIntoDatabase(Connection connection,
                                        Account account) throws Error {
        String userID = account.getUserID();
        String forename = account.getForename();
        String surname = account.getSurname();
        String emailAddress = account.getEmailAddress();
        // Cancels operation if account with this email already exists
        if (checkAccountInDatabase(connection, emailAddress)) {
            return "Account with this name already exists.";
        }
        String passwordHash = account.getPasswordHash();
        int userCustomer = account.isCustomer();
        int userStaff = account.isStaff();
        int userManager = account.isManager();
        try {
            // Query the database to insert user information
            String sql = "INSERT INTO Accounts (userID, forename, surname, " +
            "email_address, unique_password_hash, user_customer, " +
            "user_staff, user_manager) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setString(1, userID);
            statement.setString(2, forename);
            statement.setString(3, surname);
            statement.setString(4, emailAddress);
            statement.setString(5, passwordHash);
            statement.setInt(6, userCustomer);
            statement.setInt(7, userStaff);
            statement.setInt(8, userManager);

            // Execute the insert statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully registered!";
    }

    public boolean checkAccountInDatabase(Connection connection,
                                                         String emailAddress) {
        try {
            String sql = "SELECT userID FROM Accounts WHERE email_address = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();

            // Account exists if result set is not empty
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUserIDInDatabase(Connection connection, String userID) {
        try {
            String sql = "SELECT email_address FROM Accounts WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            // Account exists if result set is not empty
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String updateAccountDetails(Connection connection, String userID,
                 String forename, String surname, String emailAddress,
                                                            String password) {
        // Cancels operation if account with this userID does not exist
        if (!checkUserIDInDatabase(connection, userID))
            return "Account does not exist. Couldn't update account details.";
        try {
            // Query the database to update user information
            String sql = "UPDATE Accounts SET forename = ?, surname = ?, " +
                "email_address = ?, unique_password_hash = ? " +
                "WHERE userID = ?";

            // Set parameters for the query
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, forename);
            statement.setString(2, surname);
            statement.setString(3, emailAddress);
            String passwordHash =
                    HashedPasswordGenerator.hashPassword(password.toCharArray(),
                            userID);
            statement.setString(4, passwordHash);
            statement.setString(5, userID);

            // Executes the update statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully updated account details!";
    }

    // Main method used to test functionality
    public static void main(String[] args) {
        // setup new connection to database
        DatabaseConnectionHandler connectionHandler =
                new DatabaseConnectionHandler();
        try {
            // opens the connection
            connectionHandler.openConnection();

            Account account = new Account(List.of(UserRole.CUSTOMER),
                    "ash1@pookemon.com" , "pikballs".toCharArray(),
                    "Ash", "Ketchup");

            // initialises connection variable to be used in account operations
            Connection connection = connectionHandler.getConnection();

            AccountOperations accountOperations = new AccountOperations();
            // saves previously created account into database
            System.out.println(
                accountOperations.saveAccountIntoDatabase(connection, account));
            // updates already existing account in database
            System.out.println(accountOperations.updateAccountDetails(
                    connection, "1234", "Mike", "LEAN",
                    "mrBreast@gmail.com", "amongus2"));
        } catch (Throwable t) {
            // close connection if database crashes.
            connectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    }
}
