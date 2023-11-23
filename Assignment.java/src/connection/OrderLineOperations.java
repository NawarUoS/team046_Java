package src.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLineOperations {

    public String getOrderLineNyNumber(Connection connection, int order_line_number) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT item_quantity, product_code, order_number FROM OrderLines " +
                    "WHERE order_line_number = ? ";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order_line_number);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Integer item_quantity = resultSet.getInt("item_quantity");
                String product_code = resultSet.getString("product_code");
                Integer order_number = resultSet.getInt("order_number");

                String orderDetails = "order number is" + " " + order_number;
                return orderDetails;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Order Line not found";
    }


    public static void main(String[] args) {
    OrderLineOperations orderLineOperations = new OrderLineOperations();
    System.out.println(orderLineOperations.getOrderLineNyNumber(connection, 2));

    }

}
