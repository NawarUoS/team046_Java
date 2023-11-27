package src.model;

import src.account.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressOperations {
    public Address getAddressByUserID(Connection connection, String userID) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT house_number, city_name, street_name, " +
                    "postcode FROM Addresses WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int houseNumber =
                        resultSet.getInt("house_number");
                String streetName =
                        resultSet.getString("street_name");
                String cityName = resultSet.getString("city_name");
                String postCode = resultSet.getString("postcode");

                return new Address(userID, houseNumber, streetName, cityName,
                        postCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("User does not have address.");
    }

    public String saveAddressIntoDatabase(Connection connection,
                                          Address address) throws Error {
        // Cancels operation if account this address is being added to does
        // not exist
        String userID = address.getUserID();
        AccountOperations accountOperations = new AccountOperations();
        if (!accountOperations.checkUserIDInDatabase(connection,
                userID)) {
            return "Account you're adding this address to does not exist.";
        }
        int houseNumber = address.getHouseNumber();
        String streetName = address.getStreetName();
        String cityName = address.getCityName();
        String postCode = address.getPostCode();
        try {
            // Query the database to insert user information
            String sql = "INSERT INTO Addresses (house_number, street_name, " +
                    "city_name, postcode, userID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setInt(1, houseNumber);
            statement.setString(2, streetName);
            statement.setString(3, cityName);
            statement.setString(4, postCode);
            statement.setString(5, userID);

            // Execute the insert statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully added address to user!";
    }

    public static void main(String[] args) {
        DatabaseConnectionHandler connectionHandler =
                new DatabaseConnectionHandler();
        try {
            connectionHandler.openConnection();

            AddressOperations addressOperations = new AddressOperations();
            System.out.println(addressOperations.getAddressByUserID(connectionHandler.getConnection(),
                    "1234"));
        } catch (Throwable t) {
            // Close connection if database crashes.
            connectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    }
}
