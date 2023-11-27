package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class eraTest {
    public static String getEraByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT era_code FROM Eras " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String eras = resultSet.getString("era_code");
                return(eras);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return "Product not found.";
    }

    public static void addEras(Connection connection, String productID, List<Integer> eras) {
        try {
            for (int i = 0; i < eras.size(); i++) {
                String sql = "INSERT into Eras (" + productID +", " + eras.get(i) +")";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       // setup new connection to database
        DatabaseConnectionHandler connectionHandler =
                    new DatabaseConnectionHandler();
        try {
            connectionHandler.openConnection();

            Connection connection = connectionHandler.getConnection();
            List<Integer> eralist = new ArrayList<Integer>();
            eralist.add(2);
            eralist.add(5);
            eralist.add(3);
            addEras(connection, "T123", eralist);
            System.out.println(getEraByID(connection, "T123"));
        } catch (Throwable t) {
            // Close connection if database crashes.
            connectionHandler.closeConnection();
            throw new RuntimeException(t);
        }
    }
}

