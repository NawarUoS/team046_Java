package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartView extends JFrame {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private int orderNumber; // Assuming each CartView is associated with a unique order_number
    private JTable table;

    public CartView(Connection connection, int orderNumber) {
        // Create the JFrame in the constructor
        this.setTitle("Cart");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.connection = connection;
        this.orderNumber = orderNumber;

        setLayout(new BorderLayout());

        // Initialize CardLayout and JPanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Content for Cart Screen
        add(new JLabel("Cart Screen Content", SwingConstants.CENTER));

        // Back button to the initial screen
        JButton backButton = new JButton("Back to Main Screen");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

        // Table with data from the SQL table OrderLines
        table = createTableFromSQL();
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
        
        JButton editQuantityButton = new JButton("Edit Quantity");
        editQuantityButton.addActionListener(this::editQuantity);
        
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(this::removeOrderLine);
        
        JButton confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.addActionListener(this::confirmOrder);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editQuantityButton);
        buttonPanel.add(confirmOrderButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL() {
        String[] columnNames = {"Order Line Number", "Item Quantity", "Order Line Cost", "Product Code", "Order Number"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve data from the OrderLines table for a specific order_number
        String sqlQuery = "SELECT order_line_number, items_quantity, order_line_cost, product_code, order_number FROM OrderLines WHERE order_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_line_number"),
                        resultSet.getInt("items_quantity"),
                        resultSet.getDouble("order_line_cost"),
                        resultSet.getString("product_code"),
                        resultSet.getInt("order_number")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(model);
    }


    private void confirmOrder(ActionEvent event) {
        // Check if there are any order lines in the cart
        if (table.getRowCount() > 0) {
            try {
                // Confirm the order in the database (update the order status, for example)
                String updateQuery = "UPDATE Orders SET order_status = 'confirmed' WHERE order_number = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, orderNumber);
                    preparedStatement.executeUpdate();
                }
    
                // Show confirmation message
                JOptionPane.showMessageDialog(this, "Order confirmed successfully.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
    
                // Refresh the table after confirmation
                refreshTable(table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Show a pop-up message indicating that the cart is empty
            JOptionPane.showMessageDialog(this, "The cart is empty. Add items to the cart before confirming.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void editQuantity(ActionEvent event) {
        // Get the selected row from the table
        int selectedRow = table.getSelectedRow();
    
        if (selectedRow != -1) { // Check if a row is selected
            try {
                // Prompt the user for the new quantity
                String newQuantityStr = JOptionPane.showInputDialog(this, "Enter new quantity:");
                if (newQuantityStr != null) {
                    int newQuantity = Integer.parseInt(newQuantityStr);
    
                    // Update the quantity in the database
                    String updateQuery = "UPDATE OrderLines SET items_quantity = ? WHERE order_line_number = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setInt(1, newQuantity);
                        preparedStatement.setInt(2, (int) table.getValueAt(selectedRow, 0));
                        preparedStatement.executeUpdate();
                    }
    
                    // Show success message
                    JOptionPane.showMessageDialog(this, "Quantity updated successfully.", "Edit Quantity", JOptionPane.INFORMATION_MESSAGE);
    
                    // Refresh the table after updating quantity
                    refreshTable(table);
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit quantity.", "Edit Quantity", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeOrderLine(ActionEvent event) {
        // Get the selected row from the table
        int selectedRow = table.getSelectedRow();
    
        if (selectedRow != -1) { // Check if a row is selected
            try {
                // Get the order line number
                int orderLineNumber = (int) table.getValueAt(selectedRow, 0);
    
                // Delete the order line from the database
                String deleteQuery = "DELETE FROM OrderLines WHERE order_line_number = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, orderLineNumber);
                    preparedStatement.executeUpdate();
                }
    
                // Show success message
                JOptionPane.showMessageDialog(this, "Order line removed successfully.", "Remove Order Line", JOptionPane.INFORMATION_MESSAGE);
    
                // Refresh the table after removing order line
                refreshTable(table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to remove.", "Remove Order Line", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshTable(JTable table) {
        // Clear the existing rows in the table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    
        // Fetch and populate the table with updated data from the OrderLines table
        String sqlQuery = "SELECT order_line_number, items_quantity, order_line_cost, product_code, order_number FROM OrderLines WHERE order_number = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_line_number"),
                        resultSet.getInt("items_quantity"),
                        resultSet.getDouble("order_line_cost"),
                        resultSet.getString("product_code"),
                        resultSet.getInt("order_number")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
