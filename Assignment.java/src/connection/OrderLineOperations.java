package src.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.order.OrderLine;

public class OrderLineOperations {

    public OrderLine getOrderLineByOrderLineNumber(Connection connection, int order_line_number) {
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
        throw new Error("User does not have bank details.");
    }

}
