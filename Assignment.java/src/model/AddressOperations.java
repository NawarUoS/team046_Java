package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressOperations {
    public String getAddressByUserID(Connection connection, String userID) {
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
                String cityName = resultSet.getString("city_name");
                String streetName =
                        resultSet.getString("street_name");
                String postCode = resultSet.getString("postcode");

                // Just to test that it worked
                String addressDetails = houseNumber + ", " + postCode;
                return addressDetails;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User does not have an address.";
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
