package src.connection;

import src.account.*;
import src.order.Order;
import src.order.OrderLine;
import src.order.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderOperations {
    // add class attributes so can access them globally

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
                 
                //return new Order(OrderStatus.stringToEnum(order_status),
                        //            order_number, order_date, total_cost,
                        //new List);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Error("User does not have bank details.");
    }

    // Save account details into database
    public void saveOrder() {

    }
}
