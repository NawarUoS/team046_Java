package src.connection;

import src.account.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountOperations {
    // add class attributes so can access them globally

    // Get account details by userID
    public String getAccountByID(Connection connection, String userID) {
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
                String email_address =
                        resultSet.getString("email_address");
                String password = resultSet.getString("password");
                String user_customer =
                        resultSet.getString("user_customer");
                String user_staff =
                        resultSet.getString("user_staff");
                String user_manager =
                        resultSet.getString("user_manager");

                // Just to test that it worked
                String userDetails = forename + " " + surname + "\n" +
                        email_address;
                return userDetails;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User not found.";
    }

    // Save account details into database
    public void saveAccount() {

    }
}
