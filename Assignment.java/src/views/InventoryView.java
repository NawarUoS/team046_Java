package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.model.InventoryOperations;
import src.product.*;

import java.awt.*;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class InventoryView extends JFrame {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public InventoryView(Connection connection) {
        this.connection = connection;

        this.setTitle("Inventory View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // Content for Queue Screen
        add(new JLabel("Inventory Screen Content", SwingConstants.CENTER));

        // Table with data from the SQL table
        JTable table = createTableFromSQL();
        JScrollPane scrollPane = new JScrollPane(table);

        // Back button to the inventory screen
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

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(e -> deleteProducts(table));

        // Button takes you to packContentView, passes in content list of that pack
        JButton packViewButton = new JButton("View Pack");
        packViewButton.addActionListener(e -> {
            try {
                Integer selectedRow = table.getSelectedRow();
                String selectedID = (String) table.getValueAt(selectedRow, 0);
                try {
                    if (selectedID.substring(0,1) == "M" ||
                            selectedID.substring(0,1) == "P" ) {
                        dispose();
                        InventoryOperations inventoryOperations = new InventoryOperations();
                        List<String[]> contents = inventoryOperations.
                                getPackByID(connection, selectedID);
                        PackContentView packContentView =
                                new PackContentView(connection, contents);
                        packContentView.setVisible(true);
                    }
                } catch (StringIndexOutOfBoundsException ignore) {
                    JOptionPane.showMessageDialog(scrollPane,
                            "Please select one pack");
                }
            } catch (NullPointerException error) {
                JOptionPane.showMessageDialog(scrollPane, "Please select one pack");
            }
        });

        // Button linking to Add Product page
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

        // Button linking to Alter Stock page
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

        // Refresh button for table updates
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            dispose();
            InventoryView inventoryView =
                    new InventoryView(connection);
            inventoryView.setVisible(true);
        });

        // Button panel created and added to
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(packViewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(alterStockButton);
        buttonPanel.add(refreshButton);

        // Positioning the button and scrollpanel
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL() {
        // Creating the columns for the table, not all fields will have something in
        String[] columnNames = { "Product ID", "Brand Name", "Product Name",
                "Price", "Gauge Type", "Quantity", "Dcc Code", "Is Digital?",
                 "Is Pack?", "Eras" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve all product_code in Products table
        String sqlQuery = "SELECT product_code FROM Products";

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            // Adds all retrieved product_codes to a list of Strings
            List<String> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_code"));
            }
            InventoryOperations inventoryOperations = new InventoryOperations();
            List<Locomotive> locosList = new ArrayList<>();
            List<RollingStock> rollingList = new ArrayList<>();
            List<Controller> contList = new ArrayList<>();
            List<TrackPack> trPackList = new ArrayList<>();
            List<TrainSet> trSetList = new ArrayList<>();
            List<Track> trList = new ArrayList<>();
            // For each item in productList, retrieves the full object from the
            // database and appends to a list of that item
            for (int i = 0; i < productList.size(); i++){
                switch (productList.get(i).substring(0,1)){
                    case "L":
                        locosList.add(inventoryOperations.getLocomotiveByID(
                                connection, productList.get(i)));
                        break;
                    case "S":
                        rollingList.add(inventoryOperations.getRollingStockByID(
                                connection, productList.get(i)));
                        break;
                    case "C":
                        contList.add(inventoryOperations.getControllerByID(
                                connection, productList.get(i)));
                        break;
                    case "P":
                        trPackList.add(inventoryOperations.getTrackPackByID(
                                connection, productList.get(i)));
                        break;
                    case "M":
                        trSetList.add(inventoryOperations.getTrainSetByID(
                                connection, productList.get(i)));
                        break;
                    case "R":
                        trList.add(inventoryOperations.getTrackbyID(
                                connection, productList.get(i)));
                        break;
                    default:
                        System.out.println("Invalid code found");
                }
            }
            //Long code but logic is the same for all of them, instantiate a
            // temporary object then use getter methods to append attributes to the table
            Locomotive tempLoco;
            for (int l = 0; l < locosList.size(); l++){
                tempLoco = locosList.get(l);
                Object[] lrow = {
                        tempLoco.getProductCode(),
                        tempLoco.getBrandName(),
                        tempLoco.getProductName(),
                        tempLoco.getProductPrice(),
                        tempLoco.getGaugeType(),
                        tempLoco.getStockLevel(),
                        tempLoco.getDccCode(), "",
                        false,
                        tempLoco.getEraCode(),
                };
                model.addRow(lrow);
            }
            RollingStock tempRoller;
            for (int r = 0; r < rollingList.size(); r++) {
                tempRoller = rollingList.get(r);
                Object[] rrow = {
                        tempRoller.getProductCode(),
                        tempRoller.getBrandName(),
                        tempRoller.getProductName(),
                        tempRoller.getProductPrice(),
                        tempRoller.getGaugeType(),
                        tempRoller.getStockLevel(), "", "",
                        false,
                        tempRoller.getEraCode(),
                };
                model.addRow(rrow);
            }
            Controller tempCons;
            for (int c = 0; c < contList.size(); c++) {
                tempCons = contList.get(c);
                Object[] crow = {
                        tempCons.getProductCode(),
                        tempCons.getProductName(),
                        tempCons.getProductName(),
                        tempCons.getProductPrice(),
                        tempCons.getGaugeType(),
                        tempCons.getStockLevel(),
                        tempCons.getDigital(), "",
                        false
                };
                model.addRow(crow);
            }
            Track tempTrack;
            for (int t = 0; t < trList.size(); t++) {
                tempTrack = trList.get(t);
                Object[] trow = {
                        tempTrack.getProductCode(),
                        tempTrack.getBrandName(),
                        tempTrack.getProductName(),
                        tempTrack.getProductPrice(),
                        tempTrack.getGaugeType(),
                        tempTrack.getStockLevel(), "", "",
                        false
                };
                model.addRow(trow);
            }
            TrackPack tempTrPack;
            for (int tp = 0; tp < trPackList.size(); tp++) {
                tempTrPack = trPackList.get(tp);
                Object[] tprow = {
                        tempTrPack.getProductCode(),
                        tempTrPack.getBrandName(),
                        tempTrPack.getProductName(),
                        tempTrPack.getProductPrice(),
                        tempTrPack.getGaugeType(),
                        tempTrPack.getStockLevel(), "", "",
                        true
                };
                model.addRow(tprow);
            }
            TrainSet tempTrSet;
            for (int ts = 0; ts < trSetList.size(); ts++) {
                tempTrSet = trSetList.get(ts);
                Object[] tsrow = {
                        tempTrSet.getProductCode(),
                        tempTrSet.getBrandName(),
                        tempTrSet.getProductName(),
                        tempTrSet.getProductPrice(),
                        tempTrSet.getGaugeType(),
                        tempTrSet.getStockLevel(), "", "",
                        true
                };
                model.addRow(tsrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(model);
    }

    private void deleteProducts(JTable table) {
        int selectedRow = table.getSelectedRow();

            // Confirm the deletion with a dialog box
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the selected product?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // User confirmed deletion
            // Translate to productID
            String productID = (String) table.getValueAt(selectedRow, 0);

            // Implement the logic to delete the selected products from all tables
            // where that product may occur
            try {
                String deletePackQuery = "DELETE FROM Packs WHERE product_code = ?";
                PreparedStatement packstatement =
                        connection.prepareStatement(deletePackQuery);
                packstatement.setString(1, productID);
                packstatement.executeUpdate();

                String deleteComponentQuery = "DELETE FROM Packs WHERE component_code = ?";
                PreparedStatement compstatement =
                        connection.prepareStatement(deleteComponentQuery);
                compstatement.setString(1, productID);
                compstatement.executeUpdate();


                String deleteErasQuery = "DELETE FROM Eras WHERE product_code = ?";
                PreparedStatement erastatement =
                        connection.prepareStatement(deleteErasQuery);
                erastatement.setString(1, productID);
                erastatement.executeUpdate();

                String deleteOrders = "DELETE FROM OrderLines WHERE product_code = ?";
                PreparedStatement orderstatement =
                        connection.prepareStatement(deleteOrders);
                orderstatement.setString(1, productID);
                orderstatement.executeUpdate();

                // Delete the product in Products
                String deleteProductQuery = "DELETE FROM Products WHERE product_code = ?";
                PreparedStatement statement =
                        connection.prepareStatement(deleteProductQuery);
                statement.setString(1, productID);
                statement.executeUpdate();

                // Refresh the table after deletion
                refreshTable(table);

                // Display a confirmation message
                JOptionPane.showMessageDialog(this,
                        "Selected Products and associated packs " +
                                "and eras deleted successfully.",
                        "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select at least one product to delete.",
                    "Delete Product",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Refresh table for any changes that have been made
    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        // SQL query to retrieve confirmed orders with relevant information

        table.setModel(createTableFromSQL().getModel()); // Populate the table with updated data
    }

}
