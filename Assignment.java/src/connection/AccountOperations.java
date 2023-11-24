package src.connection;

import src.account.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountOperations {
    // add class attributes so can access them globally

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
    public List<UserRole> combineUserRoles(String userCustomer, String userStaff,
                                 String userManager) {
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
    public void saveAccountIntoDatabase(Connection connection,
                                        Account account) {
        String userID = account.getUserID();
        String forename = account.getForename();
        String surname = account.getSurname();
        String emailAddress = account.getEmailAddress();
        String password = account.getPassword();
        int userCustomer = account.getUserCustomer();
        int userStaff = account.getUserStaff();
        int userManager = account.getUserManager();
        try {
            // Query the database to fetch user information
            String sql =
                    "INSERT INTO Account VALUES (" + userID +", "+ forename +
                            ", "+ surname + ", "+
                    emailAddress +", "+ password + ", " + userCustomer + ", " +
                            userStaff + ", " + userManager + ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
