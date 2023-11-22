package src.connection;

import src.util.HashedPasswordGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// Most of this code was inspired by COM2008 Week 08 Lab 5 solution
public class LoginOperations {
    public String verifyLogin(Connection connection, String emailAddress,
                                                    String enteredPassword) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userID, unique_password_hash, " +
                    "FROM Accounts WHERE emailAddress = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailAddress);
            System.out.print(statement);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString(
                        "unique_password_hash");

                if (verifyPassword(enteredPassword.toCharArray(),
                        storedPasswordHash)) {
                    return "Login successful for user: " + emailAddress;
                } else {
                    return "Incorrect password.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User not found.";
    }

    private static boolean verifyPassword(char[] enteredPassword,
                                                 String storedPasswordHash) {
        try {
            String hashedEnteredPassword =
                    HashedPasswordGenerator.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
