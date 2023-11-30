package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.model.OrderLineOperations;
import src.util.CurrentUserCache;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartView extends JFrame {
    private Connection connection;

    private JTable currentOrdersTable;
    private JTable fulfilledOrdersTable;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public CartView(Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Cart");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.connection = connection;

        setLayout(new BorderLayout());

        // Back button to the initial screen
        JButton backButton = new JButton("Back to Main Screen");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

        // Table with data from the current orders
        currentOrdersTable = createCartTable();
        JScrollPane currentOrdersScrollPane = new JScrollPane(currentOrdersTable);

        // Table with data from fulfilled orders
        fulfilledOrdersTable = createPreviousOrdersTable();
        JScrollPane fulfilledOrdersScrollPane = new JScrollPane(fulfilledOrdersTable);

        // Header for current orders table
        JLabel currentOrdersHeader = new JLabel("Cart", SwingConstants.CENTER);
        JPanel currentOrdersPanel = new JPanel(new BorderLayout());
        currentOrdersPanel.add(currentOrdersHeader, BorderLayout.NORTH);
        currentOrdersPanel.add(currentOrdersScrollPane, BorderLayout.CENTER);

        // Header for fulfilled orders table
        JLabel fulfilledOrdersHeader = new JLabel("Previous Orders", SwingConstants.CENTER);
        JPanel fulfilledOrdersPanel = new JPanel(new BorderLayout());
        fulfilledOrdersPanel.add(fulfilledOrdersHeader, BorderLayout.NORTH);
        fulfilledOrdersPanel.add(fulfilledOrdersScrollPane, BorderLayout.CENTER);

        // Buttons for Cart actions (confirm, edit quantity, remove)
        JButton confirmOrderButton = new JButton("Confirm Order");
        JButton editQuantityButton = new JButton("Edit Quantity");
        JButton removeOrderButton = new JButton("Remove Order Line");

        // Add action listeners to buttons
        confirmOrderButton.addActionListener(e -> confirmOrder());
        editQuantityButton.addActionListener(e -> editQuantity());
        removeOrderButton.addActionListener(e -> removeOrder());

        // Add components to the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(confirmOrderButton);
        buttonPanel.add(editQuantityButton);
        buttonPanel.add(removeOrderButton);

        // Split the screen into two halves with tables
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, currentOrdersPanel, fulfilledOrdersPanel);
        splitPane.setResizeWeight(0.5);

        // Add components to the panel
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);    
    }

    private JTable createCartTable() {
        String[] columnNames = {"Order Line Number", "Item Quantity", "Order Line Cost", "Product Code", "Order Number"};
    
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
        // SQL query to retrieve order lines for orders with the status 'pending'
        String sqlQuery = "SELECT ol.order_line_number, ol.items_quantity, ol.order_line_cost, ol.product_code, ol.order_number "
                        + "FROM Orders o "
                        + "JOIN OrderLines ol ON o.order_number = ol.order_number "
                        + "WHERE o.userID = ? AND o.order_status = 'p'";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            // gets the current logged-in user ID
            preparedStatement.setString(1, CurrentUserCache.getLoggedInUser().getUserID());
    
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

    private JTable createPreviousOrdersTable() {
        String[] columnNames = {"Order Number", "Date", "Order Contents", "Order Cost", "Order Status"};
    
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
        // SQL query to retrieve orders with order status 'c' and 'f'
        String sqlQuery = "SELECT o.order_number, o.order_date, GROUP_CONCAT(ol.product_code SEPARATOR ', ') AS order_contents, "
                        + "SUM(ol.order_line_cost) AS order_cost, o.order_status "
                        + "FROM Orders o "
                        + "JOIN OrderLines ol ON o.order_number = ol.order_number "
                        + "WHERE o.userID = ? AND o.order_status IN ('c', 'f') "
                        + "GROUP BY o.order_number, o.order_date, o.order_status";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            // gets the current logged-in user ID
            preparedStatement.setString(1, CurrentUserCache.getLoggedInUser().getUserID());
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_number"),
                        resultSet.getDate("order_date"),
                        resultSet.getString("order_contents"),
                        resultSet.getDouble("order_cost"),
                        resultSet.getString("order_status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return new JTable(model);
    }
        
    private void confirmOrder() {
        // Implement the logic for confirming orders
        int selectedRow = currentOrdersTable.getSelectedRow();
        if (selectedRow != -1) {
            // Assuming orderNumber is the first column in the table
            int orderNumber = (int) currentOrdersTable.getValueAt(selectedRow, 4);
    
            // Display a confirmation dialog
            int confirmResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to confirm this order?",
                    "Confirm Order",
                    JOptionPane.YES_NO_OPTION);
    
            if (confirmResult == JOptionPane.YES_OPTION) {
                // Perform database update (modify as needed)
                updateOrderStatus(orderNumber, "c");
    
                // Notify the user that the order was confirmed
                JOptionPane.showMessageDialog(
                        this,
                        "Order #" + orderNumber + " has been confirmed successfully!",
                        "Order Confirmed",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an order to confirm.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        refreshPage();
    }
        
    private void editQuantity() {
        // Implement the logic for editing order quantity
        int selectedRow = currentOrdersTable.getSelectedRow();
        if (selectedRow != -1) {
            // Assuming orderNumber is the first column in the table
            int orderNumber = (int) currentOrdersTable.getValueAt(selectedRow, 0);
            System.out.println("Editing quantity for order: " + orderNumber);

            // Perform database update (modify as needed)
            // For example, open a dialog for quantity input and update the database
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to edit quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeOrder() {
        // Implement the logic for removing orders
        int selectedRow = currentOrdersTable.getSelectedRow();
        if (selectedRow != -1) {
            // Assuming orderNumber is the first column in the table
            int orderLineNumber = (int) currentOrdersTable.getValueAt(selectedRow, 0);

            // Perform database update (modify as needed)
            OrderLineOperations o = new OrderLineOperations();

            // Display a confirmation dialog
            int confirmResult = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to confirm this order?",
                    "Confirm Order",
                    JOptionPane.YES_NO_OPTION);
    
            if (confirmResult == JOptionPane.YES_OPTION) {
                // Perform operation 
                o.removeOrderLine(connection, orderLineNumber);
    
                // Notify the user that the order was confirmed
                JOptionPane.showMessageDialog(
                    this,
                    "Order line #" + orderLineNumber + " has been deleted successfully!",
                    "Order Line Deleted",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshPage();
    }

    private void updateOrderStatus(int orderNumber, String newStatus) {
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

    private void refreshPage() {
        // Refresh the current orders table
        DefaultTableModel currentOrdersModel = (DefaultTableModel) currentOrdersTable.getModel();
        currentOrdersModel.setRowCount(0); // Clear the existing rows
        JTable newCurrentOrdersTable = createCartTable();
        currentOrdersTable.setModel(newCurrentOrdersTable.getModel());

        // Refresh the previous orders table
        DefaultTableModel previousOrdersModel = (DefaultTableModel) fulfilledOrdersTable.getModel();
        previousOrdersModel.setRowCount(0); // Clear the existing rows
        JTable newPreviousOrdersTable = createPreviousOrdersTable();
        fulfilledOrdersTable.setModel(newPreviousOrdersTable.getModel());
    }
}
