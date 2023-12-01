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
        refreshButton.addActionListener(e -> refreshTable(table));

        // Button panel created and added to
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
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
                        tempLoco.getDdcCode(), "", "",
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
                        tempRoller.getStockLevel(), "", "", "",
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
                        tempCons.getDigital()
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
                        tempTrack.getStockLevel(),
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

    // Refresh table for any changes that have been made
    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        // SQL query to retrieve confirmed orders with relevant information

        table.setModel(createTableFromSQL().getModel()); // Populate the table with updated data
    }

}
