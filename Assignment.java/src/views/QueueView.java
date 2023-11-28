package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueueView extends JPanel {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public QueueView(Connection connection, CardLayout cardLayout, JPanel cardPanel) {
        this.connection = connection;

        setLayout(new BorderLayout());

        // Content for Queue Screen
        add(new JLabel("Queue Screen Content", SwingConstants.CENTER));

        // Back button to the initial screen
        JButton backButton = new JButton("Back to Main Screen");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

        // Table with data from the SQL table
        JTable table = createTableFromSQL();
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons to delete an order and change the status of an order
        JButton deleteButton = new JButton("Delete Order");
        deleteButton.addActionListener(e -> deleteOrder());

        JButton changeStatusButton = new JButton("Change Status");
        changeStatusButton.addActionListener(e -> changeOrderStatus());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(changeStatusButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL() {
        String[] columnNames = {"Order Number", "Order Date", "Total Cost", "Order Status", "User ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve data from your table
        String sqlQuery = "SELECT order_number, order_date, total_cost, order_status, userID FROM Orders";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_number"),
                        resultSet.getDate("order_date"), // Adjust the data type if necessary
                        resultSet.getDouble("total_cost"),
                        resultSet.getString("order_status"),
                        resultSet.getInt("userID")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(model);
    }

    private void deleteOrder() {
        // Implement the logic to delete an order
        // You can use the selected row in the table to determine the order to delete
        // For example, int selectedRow = table.getSelectedRow();
    }

    private void changeOrderStatus() {
        // Implement the logic to change the status of an order
        // You can use the selected row in the table to determine the order to update
        // For example, int selectedRow = table.getSelectedRow();
    }
}
