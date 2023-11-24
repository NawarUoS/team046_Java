package src.model;

import src.account.*;
import src.util.HashedPasswordGenerator;

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
            String sql = "SELECT forename, surname, email_address, password, " +
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
                String password = resultSet.getString("password");
                String userCustomer =
                        resultSet.getString("user_customer");
                String userStaff =
                        resultSet.getString("user_staff");
                String userManager =
                        resultSet.getString("user_manager");

                return new Account(userID, combineUserRoles(userCustomer,
                        userStaff, userManager), emailAddress, password,
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
        if (checkAccountInDatabase(connection, emailAddress)) {
            return "Account with this name already exists.";
        }
        String password = account.getPassword();
        String passwordHash = account.getPasswordHash();
        int userCustomer = account.getUserCustomer();
        int userStaff = account.getUserStaff();
        int userManager = account.getUserManager();
        try {
            // Query the database to insert user information
            String sql = "INSERT INTO Accounts (userID, forename, surname, " +
            "email_address, password, unique_password_hash, user_customer, " +
            "user_staff, user_manager) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setString(1, userID);
            statement.setString(2, forename);
            statement.setString(3, surname);
            statement.setString(4, emailAddress);
            statement.setString(5, password);
            statement.setString(6, passwordHash);
            statement.setInt(7, userCustomer);
            statement.setInt(8, userStaff);
            statement.setInt(9, userManager);

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
        if (!checkAccountInDatabase(connection, emailAddress))
            return "Account does not exist.";
        try {
            String sql = "UPDATE Accounts SET forename = ?, surname = ?, " +
                "email_address = ?, password = ?, unique_password_hash = ?" +
                "WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, forename);
            statement.setString(2, surname);
            statement.setString(3, emailAddress);
            statement.setString(4, password);
            String passwordHash =
                    HashedPasswordGenerator.hashPassword(password.toCharArray(),
                            userID);
            statement.setString(5, passwordHash);
            statement.setString(6, userID);

            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully updated account details!";
    }

    public static void main(String[] args) {
        DatabaseConnectionHandler connectionHandler =
                new DatabaseConnectionHandler();
        try {
            connectionHandler.openConnection();

            Account account = new Account(List.of(UserRole.CUSTOMER),
                    "ash@pokemon.com" , "pikchuballs",
                    "Ash", "Ketchup");
            Connection connection = connectionHandler.getConnection();

            AccountOperations accountOperations = new AccountOperations();
            System.out.println(
                accountOperations.saveAccountIntoDatabase(connection, account));
            System.out.println(accountOperations.updateAccountDetails(
                    connection, "1234", "Mike",
                    "LEAN", "mr@gmail.com",
                    "amongus"));
        } catch (Throwable t) {
            // Close connection if database crashes.
            connectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    }
}
