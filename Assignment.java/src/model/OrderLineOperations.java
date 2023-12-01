package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.order.OrderLine;

public class OrderLineOperations {
    // from cart 
    public void updateOrderLineQuantity(Connection connection, int orderLineNumber, int newQuantity) {
        try {
            // SQL query to update the quantity in the OrderLines table
            String updateQuery = "UPDATE OrderLines SET items_quantity = ? WHERE order_line_number = ?";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setInt(2, orderLineNumber);
    
                // Execute the update statement
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Order line quantity updated successfully.");
                } else {
                    System.out.println("Failed to update order line quantity.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public OrderLine getOrderLineByOrderLineNumber(Connection connection, int order_line_number) throws Error {
        try {
            // Query the database to fetch user information
            String sql = "SELECT item_quantity, order_line_cost, product_code, order_number FROM OrderLines " +
                    "WHERE order_line_number = ? ";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_line_number);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int item_quantity = resultSet.getInt("item_quantity");
                String product_code = resultSet.getString("product_code");
                double order_line_cost = resultSet.getInt("order_line_cost");
                int order_number = resultSet.getInt("order_number");

                return new OrderLine(order_line_number, product_code, item_quantity, order_line_cost, order_number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Order line not found.");
    }

    public String saveOrderLineInDatabase(Connection connection, OrderLine orderLine) {
        int orderLineNumber = orderLine.getOrderLineNumber();
        String productCode = orderLine.getProductCode();
        int quantity = orderLine.getQuantity();
        double orderLineCost = orderLine.getOrderLineCost();
        int orderNumber = orderLine.getOrderNumber();

        // Cancels operation if it finds a duplicate
        if (checkOrderLineInDatabase(connection, orderLineNumber)) {
            return "Order line of this order already exists.";
        }
        try {
            // Query the database to insert entry
            String sql = "INSERT INTO OrderLines (order_line_number," + 
            "item_quantity, order_line_cost, product_code, order_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setInt(1, orderLineNumber);
            statement.setInt(2, quantity);
            statement.setDouble(3, orderLineCost);
            statement.setString(4, productCode);
            statement.setInt(5, orderNumber);
            
            // Execute the insert statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully added order line!";
    }

    public boolean checkOrderLineInDatabase(Connection connection, int orderLineNumber) {
        try {
            // TODO check if checking the same value works 
            String sql = "SELECT order_line_number FROM OrderLines WHERE order_line_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderLineNumber);
            ResultSet resultSet = statement.executeQuery();

            // Account exists if result set is not empty
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return false;
    }

    // Refrences order line number to update 
    public String updateAccountDetails(
                        Connection connection, int orderLineNumber, 
                        int quantity, double orderLineCost, String productCode, 
                        int orderNumber) {

        // Cancels operation if order line number does not exist
        if (!checkOrderLineInDatabase(connection, orderLineNumber))
            return "Order line does not exist. Couldn't update details.";
        try {
            // Query the database to update details
            String sql = "UPDATE OrderLines SET item_quantity = ?, " +
                "order_line_cost = ?, product_code = ?, order_number = ?" +
                "WHERE order_line_number = ?";

            // Set parameters for the query
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderLineNumber);
            statement.setInt(2, quantity);
            statement.setDouble(3, orderLineCost);
            statement.setString(4, productCode);
            statement.setInt(5, orderNumber);

            // Executes the update statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Successfully updated order line details!";
    }

    public void removeOrderLine(Connection connection, int orderLineNumber) {
        // Placeholder method for removing order from the database (modify as needed)
        String sqlQuery = "DELETE FROM OrderLines WHERE order_line_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, orderLineNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
