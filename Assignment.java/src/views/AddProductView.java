package src.views;

import com.mysql.cj.log.Log;
import src.account.*;
import src.account.UserRole;
import src.model.*;
import src.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class AddProductView extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTable historyTable;

    private JTextField productIDField;
    private JTextField brandNameField;
    private JTextField productNameField;
    private JTextField priceField;
    private JTextField gaugeTypeField;
    private JTextField quantityField;
    private JTextField dccField;
    private JTextField isDigitalField;
    private JTextField erasListField;
    private JTextField componentsListField;


    public AddProductView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Add Products");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(6, 2));

        // Create JLabels for username and password
        JLabel IDLabel = new JLabel("Product ID:");
        JLabel brandLabel = new JLabel("Brand Name:");
        JLabel productLabel = new JLabel("Product Name:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel gaugeLabel = new JLabel("Gauge Type:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel dccLabel = new JLabel("DCC Code:");
        JLabel digitalLabel = new JLabel("Is Digital:");
        JLabel erasLabel = new JLabel("Eras:");
        JLabel componentLabel = new JLabel("Components:");

        // Create JTextFields for entering username and password
        productIDField = new JTextField(6);
        brandNameField = new JTextField(40);
        productNameField = new JTextField(40);
        priceField = new JTextField(7);
        gaugeTypeField = new JTextField(2);
        quantityField = new JTextField(10);
        dccField = new JTextField(40);
        isDigitalField = new JTextField(6);
        erasListField = new JTextField(50);
        componentsListField = new JTextField(50);

        // Create a JButton for the register action
        JButton addProductButton = new JButton("Register");

        // Add components to the panel
        panel.add(IDLabel);
        panel.add(productIDField);
        panel.add(brandLabel);
        panel.add(brandNameField);
        panel.add(productLabel);
        panel.add(productNameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(gaugeLabel);
        panel.add(gaugeTypeField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(dccLabel);
        panel.add(dccField);
        panel.add(digitalLabel);
        panel.add(isDigitalField);
        panel.add(erasLabel);
        panel.add(erasListField);
        panel.add(componentLabel);
        panel.add(componentsListField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(addProductButton);

        // Create an ActionListener for the register button
        addProductButton.addActionListener(e -> {
            String productID = productIDField.getText();
            String brandName = brandNameField.getText();
            String productName = productNameField.getText();
            String sPrice = priceLabel.getText();
            Double price = Double.parseDouble(sPrice);
            String gauge = gaugeTypeField.getText();
            String sQuantity = quantityField.getText();
            Integer quantity = Integer.parseInt(sQuantity);
            String dccCode = dccField.getText();
            String sIsDigital = isDigitalField.getText();
            Boolean isDigital = Boolean.parseBoolean(sIsDigital);
            String erasComp = erasListField.getText();
            String componentComp = componentsListField.getText();

            // Converting erasComp into a list<Integer>
            List<Integer> erasList = new ArrayList<Integer>();
            StringTokenizer erasTokens = new StringTokenizer(erasComp, ",");
            int erasTokenCount = erasTokens.countTokens();
            for (int i = 0; i < erasTokenCount; i++) {
                erasList.add(Integer.parseInt(erasTokens.nextToken()));
            }

            // Converting componentComp into a List<String[]>
            List<String[]> componentList = new ArrayList<>();
            String[] componentQuantity = {"", ""};
            StringTokenizer compTokens = new StringTokenizer(componentComp, ",");
            int compTokenCount = compTokens.countTokens();
            for (int i = 0; i < compTokenCount / 2; i++) {
                for (int y = 0; y < 2; y++) {
                    componentQuantity[y] = compTokens.nextToken();
                }
                componentList.add(componentQuantity);
            }
            InventoryOperations inventoryOperations = new InventoryOperations();

            switch(productID.substring(0, 1)) {
                case "L":
                    inventoryOperations.addLocomotive(connection, productID, brandName,
                            productName, price, gauge, quantity, dccCode, erasList);
                    break;
                case "S":
                    inventoryOperations.addRollingStock(connection, productID,
                            brandName, productName, price, gauge, quantity, erasList);
                    break;
                case "C":
                    inventoryOperations.addController(connection, productID, brandName,
                            productName, price, gauge, quantity, isDigital);
                    break;
                case "P":
                case "M":
                    inventoryOperations.addPacks(connection, productID, brandName,
                            productName, price, gauge, quantity, componentList);
                    break;
                case "R":
                    inventoryOperations.addProduct(connection, productID,
                            brandName, productName, price, gauge, quantity);
                default:
                    System.out.println("Invalid productID");
            }

        });
    }
}