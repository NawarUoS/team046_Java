package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        this.setTitle("Staff Dashboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.connection = connection;

        setLayout(new BorderLayout());

        // Back button to the initial screen
        JButton backButton = new JButton("Back to Main Screen");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

        // Table with data from the current orders
        currentOrdersTable = createTableFromSQL("c");
        JScrollPane currentOrdersScrollPane = new JScrollPane(currentOrdersTable);

        // Table with data from fulfilled orders
        fulfilledOrdersTable = createTableFromSQL("f");
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
        JButton removeOrderButton = new JButton("Remove Order");

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

    private JTable createTableFromSQL(String orderStatus) {
        String[] columnNames = {"Order Number", "Date", "Order Contents", "Order Cost", "Order Status"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve orders with the specified status
        String sqlQuery = "SELECT o.order_number, o.order_date, GROUP_CONCAT(ol.product_code SEPARATOR ', ') AS order_contents, "
                + "SUM(ol.order_line_cost) AS order_cost, o.order_status "
                + "FROM Orders o "
                + "JOIN OrderLines ol ON o.order_number = ol.order_number "
                + "WHERE o.userID = ? AND o.order_status = ? "
                + "GROUP BY o.order_number, o.order_date, o.order_status";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            // gets the current logged in user ID 
            preparedStatement.setString(1, CurrentUserCache.getLoggedInUser().getUserID());
            preparedStatement.setString(2, orderStatus);

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
            int orderNumber = (int) currentOrdersTable.getValueAt(selectedRow, 0);
            System.out.println("Order confirmed: " + orderNumber);

            // Perform database update (modify as needed)
            updateOrderStatus(orderNumber, "f");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to confirm.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            int orderNumber = (int) currentOrdersTable.getValueAt(selectedRow, 0);
            System.out.println("Removing order: " + orderNumber);

            // Perform database update (modify as needed)
            removeOrderFromDatabase(orderNumber);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void removeOrderFromDatabase(int orderNumber) {
        // Placeholder method for removing order from the database (modify as needed)
        String sqlQuery = "DELETE FROM Orders WHERE order_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, orderNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
