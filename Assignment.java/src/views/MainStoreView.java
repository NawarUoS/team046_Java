package src.views;

import src.account.*;
import src.inventory.Inventory;
import src.model.*;
import src.model.*;
import src.util.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainStoreView extends JFrame {
    private JTable productsTable;
    private JTable packsTable;
    private final int WIDTH = 800;
    private final int HEIGHT = 500;

    public MainStoreView(Connection connection) throws SQLException {

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
            dispose();
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
    }
}
