package src.model;

import src.account.Account;
import src.order.Order;
import src.order.OrderLine;
import src.order.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderOperations {
    // from cart view 
    public void updateOrderStatus(Connection connection, int orderNumber, String newStatus) {
        // Placeholder method for updating order status in the database (modify as needed)
        String sqlQuery = "UPDATE Orders SET order_status = ? WHERE order_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, orderNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get order details by order_number
    public Order getOrderByOrderNumber(Connection connection, int order_number) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT order_date, total_cost, order_status, " +
                    "userID FROM Orders WHERE order_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_number);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String order_date = resultSet.getString("order_date");
                double total_cost = resultSet.getDouble("order_date");
                String order_status = resultSet.getString("order_status");
                String userID = resultSet.getString("userID");
                // TODO make it return with list of order lines
                return new Order(OrderStatus.stringToEnum(order_status), order_number, order_date, total_cost, null);

            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("Order does not exist.");
    }


    public boolean checkOrderInDatabase(Connection connection, int orderNumber) {
        try {
            String sql = "SELECT orderNumber FROM Orders WHERE orderNumber = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderNumber);
            ResultSet resultSet = statement.executeQuery();

            // Order exists if result set is not empty
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Save order into database
    public String saveOrderIntoDatabase(Connection connection,
                                          Order order) throws Error {
        String orderDate = order.getOrderDate();
        double totalCost = order.getTotalCost();
        OrderStatus orderStatus  = order.getOrderStatus();
        int orderNumber = order.getOrderNumber();

        // Cancels operation if order with this orderNumber already exists
        if (checkOrderInDatabase(connection, orderNumber)) {
            return "This order already exists.";
        }

        try {
            // Query the database to insert user information
            String sql = "INSERT INTO Orders (orderNumber, orderDate, totalCost, status) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the query
            statement.setInt(1, orderNumber);
            statement.setString(2, orderDate);
            statement.setDouble(3, totalCost);
            statement.setString(4, orderStatus.name());

            // Execute the insert statement
            statement.executeUpdate();

            // Close the statement to release resources
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Order saved";
    }



    // TODO implement combine order lines into List
    public List<OrderLine> combineOrderLines() {
    
        return null;
    };
    // Save order into database
    public void saveOrder() {

    }
}
