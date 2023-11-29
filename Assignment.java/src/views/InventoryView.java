package src.views;

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class InventoryView extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTable historyTable;

    public InventoryView(Connection connection) throws SQLException {

        // Create the JFrame in the constructor
        this.setTitle("Inventory Dashboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JPanel with CardLayout to hold the different screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        this.add(cardPanel);

        // Create screens (JPanel instances)
        JPanel addProductScreen = new addProductScreen();

        // Add screens to the cardPanel with associated names
        cardPanel.add(addProductScreen, "Inventory");

        // Create buttons
        JButton queueButton = new JButton("Add Products");
        JButton historyButton = new JButton("Remove Products");

        // Add ActionListener to buttons for screen navigation
        queueButton.addActionListener(e -> cardLayout.show(cardPanel, "Add Product"));
        historyButton.addActionListener(e -> cardLayout.show(cardPanel, "Remove Product"));

        // Add buttons to the initial screen
        JPanel initialScreen = new JPanel();
        initialScreen.add(queueButton);
        initialScreen.add(historyButton);

        // Add the initial screen to the cardPanel
        cardPanel.add(initialScreen, "Initial");

        // Show the initial screen
        cardLayout.show(cardPanel, "Initial");
    }

    private class addProductScreen extends JPanel {
        public addProductScreen() {
            setLayout(new BorderLayout());

            // Content for Inventory Screen
            add(new JLabel("Add product Screen Content", SwingConstants.CENTER));

            // Back button to the initial screen
            JButton backButton = new JButton("Back to Main Screen");
            backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(backButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
}
