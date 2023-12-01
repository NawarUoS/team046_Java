package src.model;

import src.util.HashedPasswordGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// Most of this code was inspired by COM2008 Week 08 Lab 5 solution
public class LoginOperations {

    private static String userID;

    public static boolean verifyLogin(Connection connection,
                                      String emailAddress,
                              char[] enteredPassword) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userID, unique_password_hash " +
                    "FROM Accounts WHERE email_address = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userID = resultSet.getString("userID");
                String storedPasswordHash =
                        resultSet.getString("unique_password_hash");

                // Verify the entered password against the stored hash
                if (verifyPassword(enteredPassword, storedPasswordHash)) {
                    System.out.println(
                            "Login successful for user: " + emailAddress);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean verifyPassword(char[] enteredPassword,
                                                String storedPasswordHash) {
        try {
            String hashedEnteredPassword =
                    HashedPasswordGenerator.hashPassword(enteredPassword,
                            userID);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}