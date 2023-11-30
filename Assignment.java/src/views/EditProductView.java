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

public class EditProductView extends JFrame {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public EditProductView(
            Connection connection) {
        this.connection = connection;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        // Content for Queue Screen
        add(new JLabel("Edit Product Screen Content", SwingConstants.CENTER));

        // Back button to the initial screen
        JButton backButton = new JButton("Back to Staff Dashboard");
        backButton.addActionListener(e -> {
            dispose();
            try {
                StaffView staffView =
                        new StaffView(connection);
                staffView.setVisible(true);
            } catch (SQLException eb) {
                eb.printStackTrace();
            }
        });

        // Table with data from the SQL table
        JTable table = createTableFromSQL();
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons to delete an order and change the status of an order
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> {
            dispose();
            try {
                AddProductView addProductView =
                        new AddProductView(connection);
                addProductView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton alterStockButton = new JButton("Alter Stock");
        alterStockButton.addActionListener(e -> {
            dispose();
            try {
                AlterStockView alterStockView =
                        new AlterStockView(connection);
                alterStockView.setVisible(true);
            } catch (SQLException ea) {
                ea.printStackTrace();
            }
        });

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable(table));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        buttonPanel.add(alterStockButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL() {
        String[] columnNames = { "Product ID", "Brand Name", "Product Name",
                "Price", "Gauge Type", "Dcc Code", "Is Digital?",
                "Eras", "Is Pack?" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve confirmed orders with relevant information
        String sqlQuery = "SELECT product_code, brand_name, product_name, price," +
                "gauge_type, quantity, dcc_code, is_digital, is_pack FROM Products";

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("product_code"),
                        resultSet.getString("brand_name"),
                        resultSet.getString("product_name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("gauge_type"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("dcc_code"),
                        resultSet.getBoolean("is_digital"),
                        resultSet.getBoolean("is_pack")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(model);
    }

    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        // SQL query to retrieve confirmed orders with relevant information

        table.setModel(createTableFromSQL().getModel()); // Populate the table with updated data
    }

}
