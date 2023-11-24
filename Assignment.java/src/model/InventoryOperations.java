package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InventoryOperations {

    public String getGenProductByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT brand_name, product_name, gauge_type, " +
            "model_scale, quantity FROM Products " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String brandName = resultSet.getString("brand_name");
                String productName = resultSet.getString("product_name");
                String gaugeType = resultSet.getString("gauge_type");
                Integer modelScale = resultSet.getInt("model_scale");
                Integer quantity = resultSet.getInt("quantity");
                return (brandName + ", " + productName + ", " + gaugeType + ", " +
                gaugeType + ", " + modelScale + ", " + quantity);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return "Product not found.";
    }

    public String getLocomotiveByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT dcc_code FROM Locomotives " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dccCode = resultSet.getString("dcc_code");
                return (dccCode);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return "Product not found.";
    }

    public String getControllerByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT is_digital FROM Controller " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String isDigital = resultSet.getString("is_digital");
                return(isDigital);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return "Product not found.";
    }

    public String getEraByID(Connection connection, String ID) {
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

    public String getPackByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT component_code, quantity FROM Packs " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // If this kicks off use a list
                String component = resultSet.getString("component_code");
                String quantity = resultSet.getString("quantity");                
                return(component + ", " + quantity);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return "Product not found.";
    }

    public String getProductByID(Connection connection, String ID) {
        char firstLetter = ID.charAt(0);
        String type = String.valueOf(firstLetter);
        String typeProperties;
        switch (type) {
            case "C":
                typeProperties = getControllerByID(connection, ID);
                break;
            case "L":
                typeProperties = getLocomotiveByID(connection, ID) + getEraByID(connection, ID);
                break;
            case "S":
                typeProperties = getEraByID(connection, ID);
                break;
            case "M":
                typeProperties = getPackByID(connection, ID);
                break;
            case "P":
                typeProperties = getPackByID(connection, ID);
                break;
            default:
                typeProperties = "";
        }
        return (getGenProductByID(connection, ID) + typeProperties);
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
