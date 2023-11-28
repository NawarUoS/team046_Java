package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

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
        deleteButton.addActionListener(e -> deleteOrder(table));

        JButton changeStatusButton = new JButton("Change Status");
        changeStatusButton.addActionListener(e -> changeOrderStatus(table));

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

    private void deleteOrder(JTable table) {
        int selectedRow = table.getSelectedRow();
    
        if (selectedRow != -1) { // Check if a row is selected
            int orderNumber = (int) table.getValueAt(selectedRow, 0);
    
            // Confirm the deletion with a dialog box
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this order?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    
            if (option == JOptionPane.YES_OPTION) {
                // User confirmed deletion
                try {
                    // Example SQL query to delete the order
                    String deleteQuery = "DELETE FROM Orders WHERE order_number = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                        preparedStatement.setInt(1, orderNumber);
                        preparedStatement.executeUpdate();
                    }
    
                    // Refresh the table after deletion
                    refreshTable(table);
    
                    // Display a confirmation message
                    JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Delete Order", JOptionPane.WARNING_MESSAGE);
        }
    }        
    private void changeOrderStatus(JTable table) {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length > 0) { // Check if at least one row is selected
            List<Integer> orderNumbers = new ArrayList<>();

            // Prompt the user for the new order status
            String newStatus = JOptionPane.showInputDialog(this, "Enter new status (p, c, or f):");

            if (newStatus != null && (newStatus.equals("p") || newStatus.equals("c") || newStatus.equals("f"))) {
                // Collect order numbers for selected rows
                for (int selectedRow : selectedRows) {
                    int orderNumber = (int) table.getValueAt(selectedRow, 0);
                    orderNumbers.add(orderNumber);
                }

                // Implement the logic to change the status of the selected orders
                try {
                    // Example SQL query to update the order status for multiple orders
                    String updateQuery = "UPDATE Orders SET order_status = ? WHERE order_number IN (" +
                            String.join(",", orderNumbers.stream().map(String::valueOf).toArray(String[]::new)) + ")";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setString(1, newStatus);
                        preparedStatement.executeUpdate();
                    }

                    // Refresh the table after status change
                    refreshTable(table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid status. Please enter p, c, or f.", "Change Order Status", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select at least one row to change the status.", "Change Order Status", JOptionPane.WARNING_MESSAGE);
        }
    }        
    private void refreshTable(JTable table) {
        // Clear the existing rows
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    
        // Repopulate the table with updated data
        try {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        model.fireTableDataChanged();
    }    
}
