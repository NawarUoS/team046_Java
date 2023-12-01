package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.model.InventoryOperations;
import src.product.*;

import java.awt.*;
import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class PackContentView extends JFrame {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public PackContentView(Connection connection, List<String[]> contents) {
        this.connection = connection;

        this.setTitle("Pack Content View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // Content for Queue Screen
        add(new JLabel("Pack Contents View", SwingConstants.CENTER));

        // Table with data from the SQL table
        JTable table = createTableFromSQL(contents);
        JScrollPane scrollPane = new JScrollPane(table);

        // Adding a back button
        JButton backButton = new JButton("Stock View");
        backButton.addActionListener(e -> {
            dispose();
            InventoryView inventoryView =
                    new InventoryView(connection);
            inventoryView.setVisible(true);
        });

        // Refresh button for table updates
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable(table, contents));

        // Button panel created and added to
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(refreshButton);

        // Positioning the button and scrollpanel
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL(List<String[]> contents) {

        InventoryOperations inventoryOperations = new InventoryOperations();

        // Creating the columns for the table, not all fields will have something in
        String[] columnNames = {"Product ID", "Brand Name", "Product Name",
                "Price", "Gauge Type", "Dcc Code", "Is Digital?",
                "Is Pack?", "Eras", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<Locomotive> locosList = new ArrayList<>();
        List<RollingStock> rollingList = new ArrayList<>();
        List<Controller> contList = new ArrayList<>();
        List<TrackPack> trPackList = new ArrayList<>();
        List<TrainSet> trSetList = new ArrayList<>();
        List<Track> trList = new ArrayList<>();
        Integer locLen = 0;
        Integer rolLen = 0;
        Integer conLen = 0;
        Integer trpLen = 0;
        Integer trsLen = 0;
        Integer traLen = 0;
        // For each item in contents, retrieves the full object from the
        // database and appends to a list of that item
        // Used the stock level attribute to store the no. of that item
        // contained within the pack
        for (int i = 0; i < contents.size(); i++) {
            switch (contents.get(i)[0].substring(0, 1)) {
                case "L":
                    locosList.add(inventoryOperations.getLocomotiveByID(
                            connection, contents.get(i)[0]));
                    locosList.get(locLen).setStockLevel(Integer.parseInt
                                                        (contents.get(i)[1]));
                    locLen++;
                    break;
                case "S":
                    rollingList.add(inventoryOperations.getRollingStockByID(
                            connection, contents.get(i)[0]));
                    rollingList.get(rolLen).setStockLevel(Integer.parseInt
                                                        (contents.get(i)[1]));
                    rolLen++;
                    break;
                case "C":
                    contList.add(inventoryOperations.getControllerByID(
                            connection, contents.get(i)[0]));
                    contList.get(conLen).setStockLevel(Integer.parseInt
                                                        (contents.get(i)[1]));
                    conLen++;
                    break;
                case "P":
                    trPackList.add(inventoryOperations.getTrackPackByID(
                            connection, contents.get(i)[0]));
                    trPackList.get(trpLen).setStockLevel(Integer.parseInt
                                                        (contents.get(i)[1]));
                    trpLen++;
                    break;
                case "M":
                    trSetList.add(inventoryOperations.getTrainSetByID(
                            connection, contents.get(i)[0]));
                    trSetList.get(trsLen).setStockLevel(Integer.parseInt
                                                        (contents.get(i)[1]));
                    trsLen++;
                    break;
                case "R":
                    trList.add(inventoryOperations.getTrackbyID(
                            connection, contents.get(i)[0]));
                    trList.get(traLen).setStockLevel(Integer.parseInt
                                                        (contents.get(i)[1]));
                    traLen++;
                    break;
                default:
                    System.out.println("Invalid code found");
            }
        }
        //Long code but logic is the same for all of them, instantiate a
        // temporary object then use getter methods to append attributes to the table
        Locomotive tempLoco;
        for (int l = 0; l < locosList.size(); l++) {
            tempLoco = locosList.get(l);
            Object[] lrow = {
                    tempLoco.getProductCode(),
                    tempLoco.getBrandName(),
                    tempLoco.getProductName(),
                    tempLoco.getProductPrice(),
                    tempLoco.getGaugeType(),
                    tempLoco.getDccCode(), "",
                    false,
                    tempLoco.getEraCode(),
                    tempLoco.getStockLevel()
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
                    tempRoller.getGaugeType(), "", "",
                    false,
                    tempRoller.getEraCode(),
                    tempRoller.getStockLevel()
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
                    tempCons.getDigital(), "",
                    false, "",
                    tempCons.getStockLevel()
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
                    tempTrack.getGaugeType(), "", "",
                    false, "",
                    tempTrack.getStockLevel()
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
                    tempTrPack.getGaugeType(), "", "",
                    true, "",
                    tempTrPack.getStockLevel(),
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
                    tempTrSet.getGaugeType(), "", "",
                    true, "",
                    tempTrSet.getStockLevel()
            };
            model.addRow(tsrow);
        }

        return new JTable(model);
    }

    // Refresh table for any changes that have been made
    private void refreshTable(JTable table, List<String[]> contents) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        // SQL query to retrieve confirmed orders with relevant information

        table.setModel(createTableFromSQL(contents).getModel()); // Populate the table with updated data
    }

}
