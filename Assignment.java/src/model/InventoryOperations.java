package src.model;

import src.product.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InventoryOperations {

    /**
     * Gets a product object based on the ID from the 'Products' table.
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A new generic Product object, used by specific constructors
     */
    public Product getGenProductByID(Connection connection, String ID) throws Error {
        try {
            // Query the database to fetch product information
            String sql = "SELECT brand_name, product_name, price, " +
                        "gauge_type, quantity FROM Products WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Instantiating a Product object
                String brandName = resultSet.getString("brand_name");
                String productName = resultSet.getString("product_name");
                Double productPrice = resultSet.getDouble("price");
                String gaugeType = resultSet.getString("gauge_type");
                Integer quantity = resultSet.getInt("quantity");
                return new Product(ID, brandName, productName, productPrice,
                                    gaugeType, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    /**
     * Gets a Locomotive object based on the ID from the 'Products' table and
     * the 'Eras' table
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A new Locomotive object, used for displaying product data
     */
    public Locomotive getLocomotiveByID(Connection connection, String ID) throws Error {
        try {
            // Query the database to fetch product information
            String sql = "SELECT dcc_code FROM Products WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Instantiating a new Locomotive object, see src/product
                // for more details on this constructor type
                String dccCode = resultSet.getString("dcc_code");
                return new Locomotive(getGenProductByID(connection, ID),
                                        getEraByID(connection, ID), dccCode);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    /**
     * Gets a Controller object based on the ID from the 'Products' table.
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A new Controller object, used for displaying product data
     */
    public Controller getControllerByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT is_digital FROM Products " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Instantiates a new controller object, see src/product for
                // more details on this constructor type
                Boolean isDigital = resultSet.getBoolean("is_digital");
                return new Controller(getGenProductByID(connection, ID), isDigital);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    /**
     * Gets a RollingStock object based on the ID from the 'Products' table and
     * the 'Eras' table
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A new RollingStock object, used for displaying product data
     */
    public RollingStock getRollingStockByID(Connection connection, String ID) {
        //Rolling stock is only comprised of a generic product object and data
        //from the eras table, hence the short length of this function
        return new RollingStock(getGenProductByID(connection, ID),
                            getEraByID(connection, ID));
    }

    /**
     * Gets a Track object based on the ID from the 'Products' table.
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A new Track object, used for displaying product data
     */
    public Track getTrackbyID(Connection connection, String ID) {
        // Track has the no extra properties than the generic product, this
        // function only really exists to make calling functions easier
        return new Track(getGenProductByID(connection, ID));
    }

    /**
     * Gets a list of eras based on the ID from the 'Eras' table.
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A list of eras, used for constructing locomotive and controller
     *         objects
     */
    public static List<Integer> getEraByID(Connection connection, String ID) {
        try {
            // Query the database to fetch product information
            String sql = "SELECT era_code FROM Eras " +
                    "WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            // Creates a list of integers, while loop appends to the list
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

    public TrainSet getTrainSetByID(Connection connection, String ID) {
        return new TrainSet(getGenProductByID(connection, ID),
                getPackByID(connection, ID));
    }

    public TrackPack getTrackPackByID(Connection connection, String ID) {
        return new TrackPack(getGenProductByID(connection, ID),
                getPackByID(connection, ID));
    }

    /**
     * Gets an array of component/quantities based on the ID from the 'Packs'
     * table.
     *
     * @param connection The database connection.
     * @param ID         The unique product ID.
     * @return A new list of 1D string arrays, each array contains the component
     *         ID and the quantity used for constructing Pack objects
     */
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
                newComp[0] = resultSet.getString("component_code");
                newComp[1] = resultSet.getString("quantity");
                components.add(newComp);
            }
            return (components);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }
    /**
     * Adds all shared product fields to database, specific procedures add
     * specific bits of data to the database
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param brandName The name of the product manufacturer/brand
     * @param productName The name of the product
     * @param price Price of the product
     * @param gaugeType Gauge/scale of the product
     * @param quantity initial stock of the product
     */
    public static void addProduct(Connection connection, String productID,
                                  String brandName, String productName,
                                  Double price, String gaugeType, Integer quantity,
                                  Boolean isPack) {
        try {
            String sql = "INSERT INTO Products (product_code, brand_name, product_name, " +
                    "price, gauge_type, quantity, is_pack) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, productID);
            statement.setString(2, brandName);
            statement.setString(3, productName);
            statement.setDouble(4, price);
            statement.setString(5, gaugeType);
            statement.setInt(6, quantity);
            statement.setBoolean(7, isPack);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error ("Invalid product details");
        }
    }

    /**
     * Calls addProduct and addEras as well as adding dccCode to database
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param brandName The name of the product manufacturer/brand
     * @param productName The name of the product
     * @param price Price of the product
     * @param gaugeType Gauge/scale of the product
     * @param quantity initial stock of the product
     * @param dccCode type of Locomotive e.g. analogue, digital-ready
     * @param eras A list of integers containing the eras of the product
     */
    public void addLocomotive(Connection connection, String productID, String brandName,
                              String productName, Double price, String gaugeType,
                              Integer quantity, Boolean isPack, String dccCode, List<Integer> eras) {
        try {
            addProduct(connection, productID, brandName, productName, price,
                        gaugeType, quantity, isPack);
            String sql = "UPDATE Products SET dccCode = ? WHERE productID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, dccCode);
            statement.setString(2, productID);

            addEras(connection, productID, eras);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error ("Invalid product details");
        }
    }

    /**
     * Calls addProduct and addEras
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param brandName The name of the product manufacturer/brand
     * @param productName The name of the product
     * @param price Price of the product
     * @param gaugeType Gauge/scale of the product
     * @param quantity initial stock of the product
     * @param eras A list of the eras of the product, passed into addEras
     */
    public void addRollingStock(Connection connection, String productID, String brandName,
                                String productName, Double price, String gaugeType,
                                Integer quantity, Boolean isPack, List<Integer> eras) {
        addProduct(connection, productID, brandName, productName, price,
                    gaugeType, quantity, isPack);
        addEras(connection, productID, eras);
    }
    /**
     * Adds controller to database, calls addProduct and inserts isDigital into
     * database
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param brandName The name of the product manufacturer/brand
     * @param productName The name of the product
     * @param price Price of the product
     * @param gaugeType Gauge/scale of the product
     * @param quantity Initial stock of the product
     * @param isDigital Whether a controller is digital or not
     */
    public void addController(Connection connection, String productID, String brandName, String productName,
                              Double price, String gaugeType, Integer quantity, Boolean isPack, boolean isDigital) {
        try {
            addProduct(connection, productID, brandName, productName, price, gaugeType, quantity, isPack);
            String sql = "UPDATE Products SET isDigital = ? WHERE productID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBoolean(1, isDigital);
            statement.setString(2, productID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error ("Invalid product details");
        }
    }

    /**
     * Calls addProduct and adds entries to the packs database
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param brandName The name of the product manufacturer/brand
     * @param productName The name of the product
     * @param price Price of the product
     * @param gaugeType Gauge/scale of the product
     * @param quantity initial stock of the product
     * @param packContent A list of strings containing packContent in format:
     *                    List<String[productID, quantity]>
     */
    public void addPacks(Connection connection, String productID, String brandName,
                         String productName, Double price, String gaugeType,
                         Integer quantity, Boolean isPack, List<String[]> packContent) {
        try {
            addProduct(connection, productID, brandName, productName, price,
                    gaugeType, quantity, isPack);
            // Iterates through the list, passing the String[] into the loop
            for (int i = 0; i < packContent.size(); i++) {
                String sql = "INSERT into Packs(product_code, component_code, " +
                        "quantity) VALUES (?, ?, ?)";
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

    /**
     * Adds era entries into Eras database, called by addLocomotive
     * and addRollingStock
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param eras A list of ints containing the eras of the product
     */
    public static void addEras(Connection connection, String productID, List<Integer> eras) {
        try {
            // Iterates through the list of eras, adding productID and eras to
            // database
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
            throw new Error("Invalid product details");
        }
    }

    /**
     * Adds or subtracts stock from the database, quantity can be positive or
     * negative.
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @param quantity stock to be added or subtracted from the stock level,
     *                 to subtract stock pass in a negative quantity
     */
    public void addStock(Connection connection, String productID, Integer quantity) {

        // Getting the current quantity in Products and adding/subtracting
        // to get a new quantity value
        Integer currQuantity = getStock(connection, productID);
        Integer newQuantity = currQuantity + quantity;
        try {
            // Update Products table
            String sql2 = "UPDATE Products SET quantity = ? WHERE product_code = ?";
            PreparedStatement setStatement = connection.prepareStatement(sql2);
            setStatement.setInt(1, newQuantity);
            setStatement.setString(2, productID);
            setStatement.executeUpdate();
            setStatement.close();
            if (productID.substring(0,1) == "P" || productID.substring(0,1) == "M") {
                for (int i = 0; i < quantity; i++) {
                    List<String[]> packContent = getPackByID(connection, productID);
                    for (int x = 0; x < packContent.size(); x++) {
                        addStock(connection, packContent.get(x)[0],
                                (~(Integer.parseInt(packContent.get(x)[1]))-1));
                    }
                }
            }
        } catch (SQLException i) {
            i.printStackTrace();
            throw new Error("Invalid Stock change");
        }
    }

    /**
     * Gets the quantity value from the database
     *
     * @param connection The database connection.
     * @param productID The unique ID for each product
     * @return the amount of a product in stock as integer
     */
    public Integer getStock(Connection connection, String productID) {
        try {
            String sql = "SELECT quantity FROM Products WHERE product_code = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, productID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Product not found");
    }

    public ResultSet getProducts(Connection connection) {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT product_code, product_name, brand_name, " +
                    "gauge_type, dcc_code, is_digital, quantity FROM Products" +
                    " WHERE is_pack = 0";
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getPacks(Connection connection) {
        ResultSet resultSet = null;
        try {
            String sql =
                    "SELECT product_code, component_code, quantity " +
                    "FROM Packs";
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String inStock(int quantity) {
        if (quantity > 1)
            return "Yes";
        else
            return "No";
    }

    public String isDigital(boolean isDigital) {
        if (isDigital)
            return "Yes";
        else
            return "No";
    }
}
