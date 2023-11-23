package src.connection;

import src.account.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDetailsOperations {
    // add class attributes so can access them globally

    // Get order details by order_number
    public String getBankDetailsByuserID(Connection connection, String userID) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT card_number, card_name, expiry_date, " +
                    "security_code FROM BankDetails WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int card_number = resultSet.getInt("card_number");
                String card_name = resultSet.getString("card_name");
                String expiry_date = resultSet.getString("expiry_date");
                int security_code = resultSet.getInt("security_code");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Bank detils not found.";
    }

    // Save hashed(?) bank details into database
    public void saveBankDetails() {

    }
}
