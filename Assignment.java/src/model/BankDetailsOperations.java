package src.model;

import src.account.*;
import src.util.CurrentUserCache;
import src.util.HashedBankDetailsGenerator;
import src.util.HashedPasswordGenerator;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDetailsOperations {
    // add class attributes so can access them globally

    // Get order details by order_number
    public BankDetails getBankDetailsByUserID(Connection connection,
                                                String userID) throws Error {
        try {
            // Query the database to fetch user information
            String sql = "SELECT card_number_hash, card_company_name, " +
                    "expiry_date_hash, card_name FROM BankDetails WHERE " +
                    "userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String card_number_hash =
                        resultSet.getString("card_number_hash");
                String card_name =
                        resultSet.getString("card_name");
                String card_company_name = resultSet.getString(
                        "card_company_name");
                String expiry_date_hash =
                        resultSet.getString("expiry_date_hash");

                return new BankDetails(card_company_name, card_name,
                        card_number_hash, expiry_date_hash, userID);
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
        String cardNumberHash = bankDetails.getCardNumberHash();
        String expiryDateHash = bankDetails.getExpiryDateHash();

        try {
            // TODO fix sql statement when table is updated
            // Query the database to insert user information
            String sql = "INSERT INTO BankDetails (userID, card_company_name," +
                    " card_name, card_number_hash, expiry_date_hash) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setString(1, userID);
            statement.setString(2, cardName);
            statement.setString(3, cardHolder);
            statement.setString(4, cardNumberHash);
            statement.setString(5, expiryDateHash);


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
            String sql = "SELECT card_number_hash FROM BankDetails WHERE " +
                    "userID = ?";
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

    private static boolean verifyBankDetail(String bankDetail,
                                          String storedBankDetailHash) {
        try {
            String hashBankDetail =
                    HashedBankDetailsGenerator.hashBankDetail(bankDetail,
                            CurrentUserCache.getLoggedInUser().getUserID());
            return hashBankDetail.equals(storedBankDetailHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
