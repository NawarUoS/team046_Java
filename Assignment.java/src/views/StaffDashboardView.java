package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class StaffDashboardView extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public StaffDashboardView(Connection connection) throws SQLException {

        // Create the JFrame in the constructor
        this.setTitle("Staff Dashboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JPanel with CardLayout to hold the different screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        this.add(cardPanel);

        // Create screens (JPanel instances)
        JPanel inventoryScreen = new InventoryScreen();
        JPanel queueScreen = new QueueScreen();
        JPanel historyScreen = new HistoryScreen();

        // Add screens to the cardPanel with associated names
        cardPanel.add(inventoryScreen, "Inventory");
        cardPanel.add(queueScreen, "Queue");
        cardPanel.add(historyScreen, "History");

        // Create buttons
        JButton inventoryButton = new JButton("Store Inventory");
        JButton queueButton = new JButton("Orders Queue");
        JButton historyButton = new JButton("Sales History");

        // Add ActionListener to buttons for screen navigation
        inventoryButton.addActionListener(e -> cardLayout.show(cardPanel, "Inventory"));
        queueButton.addActionListener(e -> cardLayout.show(cardPanel, "Queue"));
        historyButton.addActionListener(e -> cardLayout.show(cardPanel, "History"));

        // Add buttons to the initial screen
        JPanel initialScreen = new JPanel();
        initialScreen.add(inventoryButton);
        initialScreen.add(queueButton);
        initialScreen.add(historyButton);

        // Add the initial screen to the cardPanel
        cardPanel.add(initialScreen, "Initial");

        // Show the initial screen
        cardLayout.show(cardPanel, "Initial");
    }

    private class InventoryScreen extends JPanel {
        public InventoryScreen() {
            setLayout(new BorderLayout());

            // Content for Inventory Screen
            add(new JLabel("Inventory Screen Content", SwingConstants.CENTER));

            // Back button to the initial screen
            JButton backButton = new JButton("Back to Main Screen");
            backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(backButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    private class QueueScreen extends JPanel {
        public QueueScreen() {
            setLayout(new BorderLayout());

            // Content for Queue Screen
            add(new JLabel("Queue Screen Content", SwingConstants.CENTER));

            // Back button to the initial screen
            JButton backButton = new JButton("Back to Main Screen");
            backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(backButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    private class HistoryScreen extends JPanel {
        public HistoryScreen() {
            setLayout(new BorderLayout());

            // Content for History Screen
            add(new JLabel("History Screen Content", SwingConstants.CENTER));

            // Back button to the initial screen
            JButton backButton = new JButton("Back to Main Screen");
            backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(backButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
}