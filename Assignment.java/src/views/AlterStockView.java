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

public class AlterStockView extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTable historyTable;

    private JTextField productIDField;
    private JTextField stockChangeField;


    public AlterStockView (Connection connection) throws SQLException {
         // Create the JFrame in the constructor
         this.setTitle("Alter Stock");
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setSize(500, 250);
         this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(2, 2));

        // Create JLabels for username and password
        JLabel IDLabel = new JLabel("Product ID:");
        JLabel stockLabel = new JLabel("Stock Change:");

        // Create JTextFields for entering username and password
        productIDField = new JTextField(6);
        stockChangeField = new JTextField(10);

        // Create a JButton for the register action
        JButton changeStockButton = new JButton("Change Stock");
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
        panel.add(stockLabel);
        panel.add(stockChangeField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(backButton);
        panel.add(changeStockButton);

        // Create an ActionListener for the register button
        changeStockButton.addActionListener(e -> {
            String productID = productIDField.getText();
            String stockChange = stockChangeField.getText();

            InventoryOperations inventoryOperations = new InventoryOperations();

            inventoryOperations.addStock(connection, productID,
                    Integer.parseInt(stockChange));

        });
    }
}
