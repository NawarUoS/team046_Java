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

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // Set a layout manager for the panels
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new GridLayout(1, 5));
        middlePanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new GridLayout(1, 5));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        this.add(panel);

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        productsTable = new JTable(tableModel);
        tableModel.addColumn("Product Code");
        tableModel.addColumn("Brand Name");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Gauge Type");
        tableModel.addColumn("DCC Code");
        tableModel.addColumn("Digital?");

        InventoryOperations inventoryOperations = new InventoryOperations();
        ResultSet resultSet1 = inventoryOperations.getProducts(connection);
        //ResultSet resultSet2 = inventoryOperations.getPacks(connection);

        while (resultSet1.next()) {
            tableModel.addRow(new Object[]{
                    resultSet1.getString("product_code"),
                    resultSet1.getString("brand_name"),
                    resultSet1.getString("product_name"),
                    resultSet1.getString("gauge_type"),
                    resultSet1.getString("dcc_code"),
                    resultSet1.getBoolean("is_digital")
            });
        }

        //TODO IMPLEMENT WHILE LOOP FOR RESULT SET 2

        // Create components for panel
        JScrollPane jScrollPaneProducts = new JScrollPane(productsTable);
        //JScrollPane jScrollPanePacks = new JScrollPane(packsTable);

        JButton cartButton = new JButton("Cart");
        JButton profileButton = new JButton("Profile");

        JButton addToOrderButton = new JButton("Add to Order");
        JButton saveOrderButton = new JButton("Save Order");

        // Add components to top panel
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(cartButton);
        topPanel.add(profileButton);

        // Add table to middle panel
        middlePanel.add(new JLabel("Products List"), BorderLayout.NORTH);
        middlePanel.add(jScrollPaneProducts, BorderLayout.CENTER);

        // Add components to bottom panel
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());
        bottomPanel.add(addToOrderButton);
        bottomPanel.add(saveOrderButton);
    }
}
