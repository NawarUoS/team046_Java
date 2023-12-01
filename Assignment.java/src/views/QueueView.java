package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.model.InventoryOperations;
import java.awt.*;
import java.sql.*;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

public class QueueView extends JPanel {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public QueueView(
            Connection connection, CardLayout cardLayout, JPanel cardPanel) {
        this.connection = connection;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

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

        JButton fulfillOrderButton = new JButton("Fulfill Order");
        fulfillOrderButton.addActionListener(e -> fulfillOrder(table));

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable(table));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fulfillOrderButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL() {
        String[] columnNames = { "Order Number", "Date", "Customer Info", 
                "Email", "Postal Address", "Order Contents", "Order Cost", 
                "Order Status", "Valid Bank Card" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve confirmed orders with relevant information
        String sqlQuery = "SELECT o.order_number, o.order_date, a.forename, " 
                + "a.surname, a.email_address, ad.house_number, ad.street_name, " 
                + "ad.city_name, ad.postcode, GROUP_CONCAT(ol.product_code SEPARATOR ', ') " 
                + "AS order_contents, SUM(ol.order_line_cost) AS order_cost, " 
                + "o.order_status, a.userID, b.card_number_hash FROM Orders o "
                + "JOIN Accounts a ON o.userID = a.userID "
                + "JOIN Addresses ad ON a.userID = ad.userID "
                + "JOIN OrderLines ol ON o.order_number = ol.order_number "
                + "LEFT JOIN BankDetails b ON a.userID = b.userID "
                + "WHERE o.order_status = 'c' "
                + "GROUP BY o.order_number, o.order_date, a.forename, a.surname, " 
                + "a.email_address, ad.house_number, ad.street_name, ad.city_name, " 
                + "ad.postcode, o.order_status, a.userID, b.card_number_hash";

        try (PreparedStatement preparedStatement 
                                    = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_number"),
                        resultSet.getDate("order_date"),
                        resultSet.getString("forename") + " " 
                            + resultSet.getString("surname"),
                        resultSet.getString("email_address"),
                        getAddressString(resultSet),
                        resultSet.getString("order_contents"),
                        resultSet.getDouble("order_cost"),
                        resultSet.getString("order_status"),
                        resultSet.getString("card_number_hash")
                            != null ? "Yes" : "No"
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(model);
    }

    private String getAddressString(ResultSet resultSet) throws SQLException {
        // Construct and return the postal address as a string
        return resultSet.getInt("house_number") + " " +
                resultSet.getString("street_name") + ", " +
                resultSet.getString("city_name") + ", " +
                resultSet.getString("postcode");
    }

    private void deleteOrder(JTable table) {
        int[] selectedRows = table.getSelectedRows();
    
        if (selectedRows.length > 0) { // Check if at least one row is selected
            List<Integer> orderNumbers = new ArrayList<>();
    
            // Confirm the deletion with a dialog box
            int option = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete the selected orders?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    
            if (option == JOptionPane.YES_OPTION) {
                // User confirmed deletion
                // Collect order numbers for selected rows
                for (int selectedRow : selectedRows) {
                    int orderNumber = (int) table.getValueAt(selectedRow, 0);
                    orderNumbers.add(orderNumber);
                }
    
                // Implement the logic to delete the selected orders
                try {
                    // Delete associated order lines (CASCADE DELETE)
                    String deleteOrderLinesQuery = "DELETE FROM OrderLines WHERE order_number IN (" +
                            String.join(",", orderNumbers.stream().map(String::valueOf).toArray(String[]::new)) + ")";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteOrderLinesQuery)) {
                        preparedStatement.executeUpdate();
                    }
    
                    // Delete the orders
                    String deleteOrdersQuery = "DELETE FROM Orders WHERE order_number IN (" +
                            String.join(",", orderNumbers.stream().map(String::valueOf).toArray(String[]::new)) + ")";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteOrdersQuery)) {
                        preparedStatement.executeUpdate();
                    }
    
                    // Refresh the table after deletion
                    refreshTable(table);
    
                    // Display a confirmation message
                    JOptionPane.showMessageDialog(this, 
                        "Selected orders and associated order lines deleted successfully.", 
                        "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select at least one row to delete.", "Delete Order",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
private void fulfillOrder(JTable table) {
    int[] selectedRows = table.getSelectedRows();

    if (selectedRows.length > 0) { // Check if at least one row is selected
        Map<String, Integer> productQuantities = new HashMap<>();

        // Confirm order fulfillment
        int confirmResult = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to fulfill the selected order(s)?", 
                "Fulfill Order Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmResult == JOptionPane.YES_OPTION) {
            // Implement the logic to change the status of the selected orders to "f" (fulfilled)
            try {
                // Collect product codes and quantities for selected rows
                for (int selectedRow : selectedRows) {
                    String productCode = 
                    // "Product Code" is the sixth column
                        (String) table.getValueAt(selectedRow, 5); 
                        int orderNumber = (int) table.getValueAt(selectedRow, 0);
                        int quantity = 1; 

                    // If the product code already exists in the map, update the quantity
                    if (productQuantities.containsKey(productCode)) {
                        quantity += productQuantities.get(productCode);
                    }

                    productQuantities.put(productCode, quantity);
                    
                    // Example SQL query to update the order status for the selected order
                    String updateQuery = "UPDATE Orders SET order_status = 'f' WHERE order_number = ?";
                    try (PreparedStatement preparedStatement 
                        = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setInt(1, orderNumber);
                        preparedStatement.executeUpdate();
                    }
                }

                // Update stock using the updateStock method
                updateStock(productQuantities);

                // Display a success message
                JOptionPane.showMessageDialog(this, 
                    "Order(s) fulfilled successfully!", 
                    "Fulfill Order", JOptionPane.INFORMATION_MESSAGE);

                // Refresh the table after status change
                refreshTable(table);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, 
            "Please select at least one row to fulfill the order.",
            "Fulfill Order", JOptionPane.WARNING_MESSAGE);
    }
}
    private void updateStock(Map<String, Integer> productQuantities) {
        // Assuming you have access to an instance of the InventoryOperations class
        InventoryOperations inventoryOperations = new InventoryOperations();

        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String productCode = entry.getKey();
            int quantity = entry.getValue();

            // negative quantity decreases stock levels
            inventoryOperations.addStock(connection, productCode, -quantity);
        }
    }

    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows


        table.setModel(createTableFromSQL().getModel()); // Populate the table with updated data
    }

}
