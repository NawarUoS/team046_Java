package src.views;

import src.account.*;
import src.model.*;
import src.util.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class MainStoreView extends JFrame {
    private Connection connection;

    private JTable productsTable;
    private JTable packsTable;
    private final int WIDTH = 800;
    private final int HEIGHT = 500;

    public MainStoreView(Connection connection) throws SQLException {
        this.connection = connection;
        this.setTitle("Store");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel productsPanel = new JPanel();
        JPanel packsPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // Set a layout manager for the panels
        panel.setLayout(new BorderLayout());
        productsPanel.setLayout(new BorderLayout());
        packsPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new GridLayout(1, 5));
        middlePanel.setLayout(new GridLayout(2, 1));
        bottomPanel.setLayout(new GridLayout(1, 5));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        this.add(panel);

        DefaultTableModel tableModelProducts = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        DefaultTableModel tableModelPacks = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        productsTable = new JTable(tableModelProducts);
        tableModelProducts.addColumn("Product Code");
        tableModelProducts.addColumn("Brand Name");
        tableModelProducts.addColumn("Product Name");
        tableModelProducts.addColumn("Gauge Type");
        tableModelProducts.addColumn("DCC Code");
        tableModelProducts.addColumn("Digital?");
        tableModelProducts.addColumn("In Stock?");

        InventoryOperations inventoryOperations = new InventoryOperations();
        ResultSet resultSet1 = inventoryOperations.getProducts(connection);
        ResultSet resultSet2 = inventoryOperations.getPacks(connection);

        while (resultSet1.next()) {
            tableModelProducts.addRow(new Object[]{
                    resultSet1.getString("product_code"),
                    resultSet1.getString("brand_name"),
                    resultSet1.getString("product_name"),
                    resultSet1.getString("gauge_type"),
                    resultSet1.getString("dcc_code"),
                    inventoryOperations.isDigital(resultSet1.getBoolean(
                            "is_digital")),
                    inventoryOperations.inStock(resultSet1.getInt(
                            "quantity"))

            });
        }

        packsTable = new JTable(tableModelPacks);
        tableModelPacks.addColumn("Product Code");

        while (resultSet2.next()) {
            tableModelPacks.addRow(new Object[]{
                    resultSet1.getString("product_code")
            });
        }

        // Create components for panel
        JScrollPane jScrollPaneProducts = new JScrollPane(productsTable);
        JScrollPane jScrollPanePacks = new JScrollPane(packsTable);

        JButton logoutButton = new JButton("Logout");
        JButton cartButton = new JButton("Cart & Recent Orders");
        JButton profileButton = new JButton("Profile");

        JButton discreteStaffButton = new JButton("Discrete Button");
        JButton addToOrderButton = new JButton("Add to Cart");

        // Add components to top panel
        topPanel.add(logoutButton);
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(cartButton);
        topPanel.add(profileButton);

        // Add table to middle panel
        productsPanel.add(new JLabel("Products List"), BorderLayout.NORTH);
        productsPanel.add(jScrollPaneProducts, BorderLayout.CENTER);
        packsPanel.add(new JLabel("Packs List"), BorderLayout.NORTH);
        packsPanel.add(jScrollPanePacks, BorderLayout.CENTER);

        middlePanel.add(productsPanel);
        middlePanel.add(packsPanel);

        // Add components to bottom panel
        if (CurrentUserCache.getLoggedInUser().getUserRoles().contains(
                UserRole.STAFF)) {
            bottomPanel.add(discreteStaffButton);
        } else {
            bottomPanel.add(new JLabel());
        }
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());
        bottomPanel.add(addToOrderButton);

        logoutButton.addActionListener(e -> {
            // Closes current view
            dispose();
            // Create and show the new JFrame
            try {
                CurrentUserCache.clearCache();
                LoginView loginView = new LoginView(connection);
                loginView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        profileButton.addActionListener(e -> {
            // Closes current view
            dispose();
            // Create and show the new JFrame
            try {
                ProfileView profileView = new ProfileView(connection);
                profileView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        cartButton.addActionListener(e -> {
            // Closes current login view
            dispose();
            // Create and show the new RegistrationView JFrame
            try {
                CartView cartView =
                        new CartView(connection);
                cartView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        discreteStaffButton.addActionListener(e -> {
            // Create and show the new RegistrationView JFrame
            try {
                if (CurrentUserCache.getLoggedInUser().getUserRoles().contains(
                        UserRole.STAFF))
                {
                    // Closes current login view
                    dispose();
                    StaffView staffView =
                            new StaffView(connection);
                    staffView.setVisible(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        addToOrderButton.addActionListener(e -> {
            int selectedRow = productsTable.getSelectedRow();

            if (selectedRow != -1) {
                String productCode = (String) productsTable.getValueAt(selectedRow, 0);
                String productName = (String) productsTable.getValueAt(selectedRow, 2);

                String quantityString = JOptionPane.showInputDialog(this, "Enter quantity for " + productName + ":");

                if (quantityString != null && !quantityString.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityString);

                        if (checkInventoryAvailability(productCode, quantity)) {
                            addToCart(productCode, quantity);

                            //JOptionPane.showMessageDialog(this, "Product added to cart successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Insufficient quantity in store inventory.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to add to the cart.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean checkInventoryAvailability(String productCode, int requestedQuantity) {
        // Implement logic to check if the requested quantity is available in the store inventory
        // Placeholder implementation:
        InventoryOperations i = new InventoryOperations();
        int availableQuantity = i.getStock(connection, productCode);
        return requestedQuantity <= availableQuantity;
    }

    private void addToCart(String productCode, int quantity) {
        // Ensure that the product code and quantity are valid
        if (productCode == null || productCode.isEmpty() || quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid product code or quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Check if the user has a pending order
            int pendingOrderNumber = getPendingOrderNumber();

            // If the user doesn't have a pending order, create one
            if (pendingOrderNumber == -1) {
                pendingOrderNumber = createPendingOrder();
            }
            // Calculate the order line cost
            double orderLineCost = calculateOrderLineCost(productCode, quantity);

            // Insert the order line into the database
            String insertQuery = "INSERT INTO OrderLines (order_line_number, items_quantity, order_line_cost, product_code, order_number) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                int orderLineNumber = generateUniqueOrderLineNumber(); // Implement this method
                int orderNumber = getPendingOrderNumber();

                insertStatement.setInt(1, orderLineNumber);
                insertStatement.setInt(2, quantity);
                insertStatement.setDouble(3, orderLineCost);
                insertStatement.setString(4, productCode);
                insertStatement.setInt(5, orderNumber);

                // Execute the INSERT statement
                insertStatement.executeUpdate();

                // Display a success message
                JOptionPane.showMessageDialog(this, "Product added to cart successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            JOptionPane.showMessageDialog(this, "Error adding product to cart.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
private int getPendingOrderNumber() throws SQLException {
    String query = "SELECT order_number FROM Orders WHERE userID = ? AND order_status = 'p'";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, CurrentUserCache.getLoggedInUser().getUserID());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("order_number");
        } else {
            return -1;
            //throw new SQLException("No pending order found for the user.");
        }
    }
}    //getPendingOrderNumber() == -1

    private int createPendingOrder() throws SQLException {
        if (getPendingOrderNumber() == -1) {
            try {
                // Generate a unique order number
                int orderNumber = generateUniqueOrderNumber();

                // Example SQL query to insert a new pending order with all fields initialized
                String insertQuery = "INSERT INTO Orders (order_number, order_date, total_cost, order_status, userID) " +
                        "VALUES (?, CURRENT_DATE, 0, 'p', ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, orderNumber);
                    preparedStatement.setString(2, CurrentUserCache.getLoggedInUser().getUserID());
                    preparedStatement.executeUpdate();
                }

                return orderNumber;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // If a pending order already exists, return its order number
        return getPendingOrderNumber();
    }   

    private double calculateOrderLineCost(String productCode, int quantity) throws SQLException {
        // Ensure that quantity is positive
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }
    
        // Example SQL query to retrieve the price from OrderLines for the given product code
        String query = "SELECT price FROM Products WHERE product_code = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productCode);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                // Retrieve the price from the OrderLines table
                double price = resultSet.getDouble("price");
    
                // Calculate the order line cost by multiplying price and quantity
                return price * quantity;
            } else {
                throw new SQLException("Price not found for the product code: " + productCode);
            }
        }
    }

    private int generateUniqueOrderLineNumber() {
    // Generate a unique order line number using timestamp and random number
    long timestamp = System.currentTimeMillis();
    int randomSuffix = new Random().nextInt(1000); // Adjust the range as needed

    return (int) (timestamp + randomSuffix);
    }

    private int generateUniqueOrderNumber() throws SQLException {
        String query = "SELECT MAX(order_number) FROM Orders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxOrderNumber = resultSet.getInt(1);
                // Increment the max order number to generate a unique order number
                return maxOrderNumber + 1;
            } else {
                // If no existing orders, start with order number 1
                return 1;
            }
        }
    }
}

