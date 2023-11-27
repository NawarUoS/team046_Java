package src.model;

import src.product.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InventoryOperations {
    /**
     * Gets the userId based on the username from the 'Users' table.
     *
     * @param connection The database connection.
     * @param username   The username for which to retrieve the userId.
     * @return The userId corresponding to the given username.
     */
    public Product getGenProductByID(Connection connection, String ID) throws Error{
        try {
            // Query the database to fetch product information
            String sql = "SELECT brand_name, product_name, product_price, gauge_type, " +
            "model_scale, quantity FROM Products " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String brandName = resultSet.getString("brand_name");
                String productName = resultSet.getString("product_name");
                Double productPrice = resultSet.getDouble("productPrice");
                String gaugeType = resultSet.getString("gauge_type");
                Integer modelScale = resultSet.getInt("model_scale");
                Integer quantity = resultSet.getInt("quantity");
                return new Product(ID, brandName, productName, productPrice, gaugeType, modelScale, quantity);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public Locomotive getLocomotiveByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT dcc_code FROM Locomotives " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dccCode = resultSet.getString("dcc_code");
                return new Locomotive(getGenProductByID(connection, ID), getEraByID(connection, ID), dccCode);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public Controller getControllerByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT is_digital FROM Controller " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Boolean isDigital = resultSet.getBoolean("is_digital");
                return new Controller(getGenProductByID(connection, ID), isDigital);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public List<Integer> getEraByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT era_code FROM Eras " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            List<Integer> eras = new ArrayList<Integer>();
            while (resultSet.next()) {
                eras.add(resultSet.getInt("era_code"));
                return(eras);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public List<String[]> getPackByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT component_code, quantity FROM Packs " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            List<String[]> components = new ArrayList<String[]>();
            String[] newComp = new String[]{"1", "2"};
            while (resultSet.next()) {
                // If this kicks off use a list
                newComp[0] = (resultSet.getString("component_code"));
                newComp[1] = resultSet.getString("quantity");  
                components.add(newComp);
            }
            return (components);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public void addProduct(Connection connection, String productID, String brandName, String productName, 
                        String gaugeType, String modelScale, String quantity) {
        try {
            String sql = "INSERT INTO Products VALUES (" + productID +", "+ brandName +", "+ gaugeType +", "+ 
                                                        modelScale +", "+ quantity +")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLocomotive(Connection connection, String productID, String dccCode, List<String> eras) {
        try {
            String sql = "INSERT into Locomotives (" + productID +", "+ dccCode + ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
            addEras(connection, productID, eras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRollingStock(Connection connection, String productID, List<String> eras ) {
        addEras(connection, productID, eras);
    }

    public void addEras(Connection connection, String productID, List<String> eras) {
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

    public void addController(Connection connection, String productID, boolean isDigital) {
        try {
            String sql = "INSERT into Controller (" + productID +", "+ isDigital +")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public void addPacks(Connection connection, String productID, String[][] packContent) {
        try {
            for (int i = 0; i < packContent.length; i++) {
                String sql = "INSERT into Packs(" + productID +", "+ packContent[i][0] +
                            ","+ packContent[i][1] +")";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeQuery();
            } 
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
}
