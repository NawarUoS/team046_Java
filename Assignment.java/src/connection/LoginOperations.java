package src.connection;

import src.util.HashedPasswordGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginOperations {
    public String verifyLogin(Connection connection, String username,
                                                    String enteredPassword) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userId, password_hash, " +
                    "FROM Users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString(
                        "password_hash");
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
