package src.model;

import src.account.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDetailsOperations {
    // add class attributes so can access them globally

    // Get order details by order_number
    public BankDetails getBankDetailsByUserID(Connection connection, String userID) throws Error {
        try {
            // Query the database to fetch user information
            String sql = "SELECT card_number, card_company_name, expiry_date," +
                    " card_name, " +
                    "security_code FROM BankDetails WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int card_number = resultSet.getInt("card_number");
                String card_name =
                        resultSet.getString("card_name");
                String card_company_name = resultSet.getString(
                        "card_company_name");
                String expiry_date =
                        resultSet.getString("expiry_date");
                int security_code =
                        resultSet.getInt("security_code");

                return new BankDetails(card_company_name, card_name,
                        card_number,
                        expiry_date, security_code, userID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("User does not have bank details.");
    }

    // Save account into database
    public String saveBankDetailsIntoDatabase(Connection connection,
                                        BankDetails bankDetails) throws Error {
        // TODO check for aborting conditions 
        String userID = bankDetails.getUserID();
        String cardName = bankDetails.getCardName();
        String cardHolder = bankDetails.getCardHolder();
        long cardNumber = bankDetails.getCardNumber();
        String expiryDate = bankDetails.getExpiryDate();
        int securityCode = bankDetails.getSecurityCode();

        try {
            // TODO fix sql statement when table is updated
            // Query the database to insert user information
            String sql = "INSERT INTO BankDetails (userID, card_company_name," +
                    " " +
                    "card_name, card_number, expiry_date, " +
                    "security_code) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setString(1, userID);
            statement.setString(2, cardName);
            statement.setString(3, cardHolder);
            statement.setLong(4, cardNumber);
            statement.setString(5, expiryDate);
            statement.setInt(6, securityCode);


            // Execute the insert statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully added bank details to user!";
    }
 
//    public String updateBankDetails(Connection connection, String userID,
//                            String cardName, String cardHolder, int cardNumber,
//                            String expiryDate, int securityCode) {
//        // Cancels operation if account with this userID does not exist
//        if (!checkBankDetailsInDatabase(connection, userID))
//            return "Account does not exist. Couldn't update bank details.";
//        try {
//            // Query the database to update user information
//            String sql = "UPDATE BankDetails SET card_company_name = ?," +
//                    " card_name = ?, expiry_date = ?, security_code = ? " +
//                    "WHERE userID = ?";
//
//            // Set parameters for the query
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, userID);
//            statement.setString(2, cardName);
//            statement.setString(3, cardHolder);
//            statement.setInt(4, cardNumber);
//            statement.setString(5, expiryDate);
//            statement.setLong(6, securityCode);
//
//            // Executes the update statement
//            statement.executeUpdate();
//
//            // Close the statement to release resources
//            statement.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "Successfully updated bank details!";
//    }

    // Refrences card_number to update 
    public boolean checkBankDetailsInDatabase(Connection connection, String userID) {
        try {
            String sql = "SELECT card_number FROM BankDetails WHERE userID = ?";
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


    // Save hashed(?) bank details into database
    public void saveBankDetails() {

    }
}
