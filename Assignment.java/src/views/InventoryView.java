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

    public InventoryView(
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
                "Price", "Gauge Type", "Quantity", "Dcc Code", "Is Digital?",
                 "Is Pack?", "Eras" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve confirmed orders with relevant information
        String sqlQuery = "SELECT product_code FROM Products";

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

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

    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        // SQL query to retrieve confirmed orders with relevant information

        table.setModel(createTableFromSQL().getModel()); // Populate the table with updated data
    }

}
