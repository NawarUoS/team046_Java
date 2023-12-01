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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class EditProductView extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

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
    Boolean flag;
    Boolean isPack;

    public void clearTextPanels(){
        productIDField.setText("");
        brandNameField.setText("");
        productNameField.setText("");
        priceField.setText("");
        gaugeTypeField.setText("");
        quantityField.setText("");
        dccField.setText("");
        isDigitalField.setText("");
        erasListField.setText("");
        componentsListField.setText("");
    }

    public EditProductView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Edit Products");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        JButton changeButton = new JButton("Change");

        JButton backButton = new JButton("Stock View");
        backButton.addActionListener(e -> {
            dispose();
            InventoryView inventoryView =
                    new InventoryView(connection);
            inventoryView.setVisible(true);
        });

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
        panel.add(backButton);
        panel.add(changeButton);

        // Create an ActionListener for the register button
        changeButton.addActionListener(e -> {
            flag = true;
            String productID = productIDField.getText();
            String brandName = brandNameField.getText();
            String productName = productNameField.getText();
            String sPrice = priceField.getText();
            String gauge = gaugeTypeField.getText();
            String sQuantity = quantityField.getText();
            String dccCode = dccField.getText();
            String sIsDigital = isDigitalField.getText();
            String erasComp = erasListField.getText();
            String componentComp = componentsListField.getText();

            ArrayList<String> productCodes = new ArrayList<>(4);
            productCodes.add("R");
            productCodes.add("C");
            productCodes.add("L");
            productCodes.add("S");
            productCodes.add("M");
            productCodes.add("P");
            try {
                Boolean check = productCodes.contains(productID.substring(0, 1));
                if (productID.length() > 6 || !check) {
                    JOptionPane.showMessageDialog(panel, "ProductID is not valid");
                    clearTextPanels();
                    flag = false;
                }
            } catch (StringIndexOutOfBoundsException ignore) {
                JOptionPane.showMessageDialog(panel, "ProductID is not valid");
            }

            if (componentComp.length() < 3) {
                isPack = false;
            } else {
                isPack = true;
            }


            // Converting erasComp into a list<Integer>
            List<Integer> erasList = new ArrayList<Integer>();
            StringTokenizer erasTokens = new StringTokenizer(erasComp, ",");
            int erasTokenCount = erasTokens.countTokens();
            for (int i = 0; i < erasTokenCount; i++) {
                erasList.add(Integer.parseInt(erasTokens.nextToken()));
                System.out.println(erasList.get(i));
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
            if (flag) {
                InventoryOperations inventoryOperations = new InventoryOperations();

                // Goes through and checks if fields have valid data in, if they
                // do, then calls method to update database.
                if (brandName.length() >= 2) {
                    try {
                        String brandSQL = "UPDATE Products SET brand_name = ? " +
                                "WHERE product_code = ?";
                        PreparedStatement brandStatement =
                                connection.prepareStatement(brandSQL);
                        brandStatement.setString(1, brandName);
                        brandStatement.setString(2, productID);
                        brandStatement.executeUpdate();
                        brandStatement.close();
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                }

                if (productName.length() >= 2) {
                    try {
                        String productSQL = "UPDATE Products SET product_name = ? " +
                                "WHERE product_code = ?";
                        PreparedStatement productStatement =
                                connection.prepareStatement(productSQL);
                        productStatement.setString(1, productName);
                        productStatement.setString(2, productID);
                        productStatement.executeUpdate();
                        productStatement.close();
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                }

                try {
                    Double price = Double.parseDouble(sPrice);
                    try {
                        String priceSQL = "UPDATE Products SET price = ? " +
                                "WHERE product_code = ?";
                        PreparedStatement priceStatement =
                                connection.prepareStatement(priceSQL);
                        priceStatement.setDouble(1, price);
                        priceStatement.setString(2, productID);
                        priceStatement.executeUpdate();
                        priceStatement.close();
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                } catch (NumberFormatException ignore) {

                }

                if (Objects.equals(gauge, "OO") ||
                        Objects.equals(gauge, "N") ||
                        Objects.equals(gauge, "TT")) {
                    try {
                        String gaugeSQL = "UPDATE Products SET gauge_type = ? " +
                                "WHERE product_code = ?";
                        PreparedStatement gaugeStatement =
                                connection.prepareStatement(gaugeSQL);
                        gaugeStatement.setString(1, gauge);
                        gaugeStatement.setString(2, productID);
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                }

                try {
                    Integer quantity = Integer.parseInt(sQuantity);
                    try {
                        String quantitySQL = "UPDATE Products SET quantity = ? " +
                                "WHERE product_code = ?";
                        PreparedStatement quantityStatement =
                                connection.prepareStatement(quantitySQL);
                        quantityStatement.setDouble(1, quantity);
                        quantityStatement.setString(2, productID);
                        quantityStatement.executeUpdate();
                        quantityStatement.close();
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                } catch (NumberFormatException ignore) {
                }

                if (dccCode.length() >= 2) {
                    try {
                        String dccSQL = "UPDATE Products SET dcc_code = ? " +
                                "WHERE product_code = ?";
                        PreparedStatement dccStatement =
                                connection.prepareStatement(dccSQL);
                        dccStatement.setString(1, dccCode);
                        dccStatement.setString(2, productID);
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                }

                if (sIsDigital.equalsIgnoreCase("true") ||
                        sIsDigital.equalsIgnoreCase("false")) {
                    Boolean isDigital = Boolean.parseBoolean(sIsDigital);
                    try {
                        String digiSQL = "UPDATE Products SET is_digital = ? " +
                                "WHERE products_code = ?";
                        PreparedStatement digiStatement =
                                connection.prepareStatement(digiSQL);
                        digiStatement.setBoolean(1, isDigital);
                        digiStatement.setString(2, productID);
                    } catch (SQLException be) {
                        be.printStackTrace();
                    }
                }
            }
        });
    }
}
