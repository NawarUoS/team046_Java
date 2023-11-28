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
        // Placeholder method, replace with actual logic to confirm the order
        JOptionPane.showMessageDialog(this, "Order confirmed successfully.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editQuantity(ActionEvent event) {
        // Placeholder method, replace with logic to edit the quantity
        JOptionPane.showMessageDialog(this, "Editing quantity...", "Edit Quantity", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeOrderLine(ActionEvent event) {
        // Placeholder method, replace with logic to remove the order line
        JOptionPane.showMessageDialog(this, "Removing order line...", "Remove Order Line", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable() {
        // Same as before
    }
}
