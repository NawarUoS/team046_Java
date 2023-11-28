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
     * @param ID         The username for which to retrieve the userId.
     * @return The userId corresponding to the given username.
     */
    public Product getGenProductByID(Connection connection, String ID) throws Error {
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
                Integer quantity = resultSet.getInt("quantity");
                return new Product(ID, brandName, productName, productPrice, gaugeType, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public Locomotive getLocomotiveByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT dcc_code FROM Product " +
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
            String sql = "SELECT is_digital FROM Product " +
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

    public RollingStock getRollingStockByID(Connection connection, String ID) {
        return new RollingStock(getGenProductByID(connection, ID), getEraByID(connection, ID));
    }

    public Track getTrackbyID(Connection connection, String ID) {
        return new Track(getGenProductByID(connection, ID));
    }

    public static List<Integer> getEraByID(Connection connection, String ID) {
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
            }
            return eras;

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

    public static void addProduct(Connection connection, String productID, String brandName, String productName,
                                  Double price, String gaugeType, Integer quantity) {
        try {
            String sql = "INSERT INTO Products (product_code, brand_name, product_name, price, " +
                    "gauge_type, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, productID);
            statement.setString(2, brandName);
            statement.setString(3, productName);
            statement.setDouble(4, price);
            statement.setString(5, gaugeType);
            statement.setInt(6, quantity);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLocomotive(Connection connection, String productID, String brandName, String productName,
                              Double price, String gaugeType, Integer quantity, String dccCode, List<Integer> eras) {
        try {
            addProduct(connection, productID, brandName, productName, price, gaugeType, quantity);
            String sql = "UPDATE Products SET dccCode = ? WHERE productID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, dccCode);
            statement.setString(2, productID);
            addEras(connection, productID, eras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRollingStock(Connection connection, String productID, String brandName, String productName,
                                Double price, String gaugeType, Integer quantity, List<Integer> eras) {
        addProduct(connection, productID, brandName, productName, price, gaugeType, quantity);
        addEras(connection, productID, eras);
    }

    public void addController(Connection connection, String productID, String brandName, String productName,
                              Double price, String gaugeType, Integer quantity, boolean isDigital) {
        try {
            addProduct(connection, productID, brandName, productName, price, gaugeType, quantity);
            String sql = "UPDATE Products SET isDigital = ? WHERE productID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBoolean(1, isDigital);
            statement.setString(2, productID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addEras(Connection connection, String productID, List<Integer> eras) {
        try {
            for (int i = 0; i < eras.size(); i++) {
                String sql = "INSERT into Eras (product_code, era_code) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, productID);
                statement.setInt(2, eras.get(i));

                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addPacks(Connection connection, String productID, List<String[]> packContent) {
        try {
            for (int i = 0; i < packContent.size(); i++) {
                String sql = "INSERT into Packs(product_code, component_code, quantity) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, productID);
                statement.setString(2, packContent.get(i)[0]);
                statement.setString(3, packContent.get(i)[1]);

                statement.executeUpdate();

                statement.close();
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public void addStock (Connection connection, String productID, Integer quantity) {
        try {
            String sql1 = "SELECT quantity FROM products WHERE product_code = ?";
            PreparedStatement getStatement = connection.prepareStatement(sql1);

            getStatement.setString(1, productID);

            ResultSet resultSet = getStatement.executeQuery();

            if (resultSet.next()) {
                Integer currQuantity = resultSet.getInt("quantity");
                Integer newQuantity = currQuantity + quantity;
                try {
                    String sql2 = "UPDATE Products SET quantity = ? WHERE product_code = ?";
                    PreparedStatement setStatement = connection.prepareStatement(sql2);

                    setStatement.setInt(1, newQuantity);
                    setStatement.setString(2, productID);

                    getStatement.executeUpdate();
                    getStatement.close();

                } catch (SQLException i) {
                    i.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
