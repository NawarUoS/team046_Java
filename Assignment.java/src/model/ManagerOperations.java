package src.model;

import src.account.Account;
import src.account.UserRole;
import src.util.CurrentUserCache;
import src.util.HashedPasswordGenerator;

import java.lang.management.MemoryNotificationInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManagerOperations {
    public ResultSet getStaff(Connection connection) {
        ResultSet resultSet = null;
        try {
            // Execute the SQL query
            String sqlQuery = "SELECT userID, forename, surname, " +
                    "email_address, user_staff FROM Accounts " +
                    "WHERE user_staff = 1";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String promoteUsersToStaff(Connection connection,
                                                     List<String> emails) {
        int numberOfPromotedPoos = 0;

        AccountOperations accountOperations = new AccountOperations();
        for (String email : emails) {
            try {
                if (!accountOperations.checkAccountInDatabase(connection,
                        email)) {
                    break;
                }
                // Query the database to update user information
                String sql = "UPDATE Accounts SET user_staff = ? " +
                        "WHERE email_address = ?";

                // Set parameters for the query
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, 1);
                statement.setString(2, email);

                // Executes the update statement
                statement.executeUpdate();

                // Close the statement to release resources
                statement.close();

                numberOfPromotedPoos++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (numberOfPromotedPoos == 0)
            return "User(s) do not exist.";
        else
            return "Successfully promoted " + numberOfPromotedPoos + " user(s)";
    }

    public String dismissUserFromStaff(Connection connection, String email) {
        AccountOperations accountOperations = new AccountOperations();
        if (!accountOperations.checkAccountInDatabase(connection, email)) {
            return "Account does not exist. Couldn't dismiss user from " +
                    "staff.";
        }
        if (accountOperations.getAccountByEmail(connection,
                email).getUserRoles().contains(UserRole.MANAGER)) {
            return "User is Manager, cannot dismiss manager.";
        }
        try {
            // Query the database to update user information
            String sql = "UPDATE Accounts SET user_staff = ? " +
                    "WHERE email_address = ?";

            // Set parameters for the query
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, 0);
            statement.setString(2, email);

            // Executes the update statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully dismissed user from staff.";
    }

    public static void main(String[] args) {
        DatabaseConnectionHandler connectionHandler =
                new DatabaseConnectionHandler();
        try {
            // opens the connection
            connectionHandler.openConnection();

            // initialises connection variable to be used in account operations
            Connection connection = connectionHandler.getConnection();

            ManagerOperations managerOperations = new ManagerOperations();
            System.out.println(
                    managerOperations.promoteUsersToStaff(connection,
                            List.of("email")));
        } catch (Throwable t) {
            // close connection if database crashes.
            connectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    }
}
