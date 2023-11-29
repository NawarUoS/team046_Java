package src.views;

import src.account.Account;
import src.account.Address;
import src.account.UserRole;
import src.model.AccountOperations;
import src.model.AddressOperations;

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class InventoryView extends JFrame {

//    private CardLayout cardLayout;
//    private JPanel cardPanel;
//    private JTable historyTable;
//
//    private JTextField productIDField;
//    private JTextField brandNameField;
//    private JTextField productNameField;
//    private JFormattedTextField priceField;
//    private JTextField gaugeTypeField;
//    private JFormattedTextField quantityField;
//    private JTextField isDigitalField;
//    private JTextField erasListField;
//    private JTextField componentsListField;
//
//    public InventoryView(Connection connection) throws SQLException {
//
//
//        // Create the JFrame in the constructor
//        this.setTitle("Inventory Dashboard");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(800, 500);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//        // Create a JPanel with CardLayout to hold the different screens
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//        this.add(cardPanel);
//
//        // Create screens (JPanel instances)
//        JPanel addProductScreen = new addProductScreen();
//        JPanel alterStockScreen = new alterStockScreen();
//
//        // Add screens to the cardPanel with associated names
//        cardPanel.add(addProductScreen, "Add Products");
//        cardPanel.add(alterStockScreen, "Alter Stock");
//
//        // Create buttons
//        JButton addButton = new JButton("Add Products");
//        JButton alterButton = new JButton("Alter Stock");
//
//        // Add ActionListener to buttons for screen navigation
//        addButton.addActionListener(e -> cardLayout.show(cardPanel, "Add Products"));
//        alterButton.addActionListener(e -> cardLayout.show(cardPanel, "Alter Stock"));
//
//        // Add buttons to the initial screen
//        JPanel initialScreen = new JPanel();
//        initialScreen.add(addButton);
//        initialScreen.add(alterButton);
//
//        // Add the initial screen to the cardPanel
//        cardPanel.add(initialScreen, "Initial");
//
//        // Show the initial screen
//        cardLayout.show(cardPanel, "Initial");
//    }
//
//    private class addProductScreen extends JPanel {
//        public addProductScreen() {
//            setLayout(new BorderLayout());
//
//            // Content for Inventory Screen
//            add(new JLabel("Add product Screen Content", SwingConstants.CENTER));
//
//            // Back button to the initial screen
//            JButton backButton = new JButton("Back to Main Screen");
//            backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));
//
//            JPanel buttonPanel = new JPanel();
//            buttonPanel.add(backButton);
//            add(buttonPanel, BorderLayout.SOUTH);
//        }
//    }
//    public RegistrationView (Connection connection) throws SQLException {
//        // Create the JFrame in the constructor
//        this.setTitle("Register");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(500, 250);
//
//        // Create a JPanel to hold the components
//        JPanel panel = new JPanel();
//        this.add(panel);
//
//        // Set a layout manager for the panel (e.g., GridLayout)
//        panel.setLayout(new GridLayout(5, 2));
//
//        // Create JLabels for username and password
//        JLabel forenameLabel = new JLabel("Forename:");
//        JLabel surnameLabel = new JLabel("Surname");
//        JLabel usernameLabel = new JLabel("Email:");
//        JLabel passwordLabel = new JLabel("Password:");
//        JLabel houseNumberLabel = new JLabel("House Number:");
//        JLabel streetNameLabel = new JLabel("Street:");
//        JLabel cityNameLabel = new JLabel("City:");
//        JLabel postCodeLabel = new JLabel("Postcode:");
//
//        // Create JTextFields for entering username and password
//        forenameField = new JTextField(40);
//        surnameField = new JTextField(40);
//        usernameField = new JTextField(40);
//        passwordField = new JPasswordField(40);
//        houseNumberField = new JTextField(40);
//        streetNameField = new JTextField(40);
//        cityNameField = new JTextField(40);
//        postCodeField = new JTextField(40);
//
//        // Create a JButton for the register action
//        JButton registerButton = new JButton("Register");
//        JButton loginButton = new JButton("Login Instead");
//
//        // Add components to the panel
//        panel.add(forenameLabel);
//        panel.add(forenameField);
//        panel.add(surnameLabel);
//        panel.add(surnameField);
//        panel.add(usernameLabel);
//        panel.add(usernameField);
//        panel.add(passwordLabel);
//        panel.add(passwordField);
//        panel.add(houseNumberLabel);
//        panel.add(houseNumberField);
//        panel.add(streetNameLabel);
//        panel.add(streetNameField);
//        panel.add(cityNameLabel);
//        panel.add(cityNameField);
//        panel.add(postCodeLabel);
//        panel.add(postCodeField);
//        panel.add(new JLabel());
//        panel.add(new JLabel());
//        panel.add(loginButton);
//        panel.add(registerButton);
//
//        // Create an ActionListener for the register button
//        registerButton.addActionListener(e -> {
//            String forename = forenameField.getText();
//            String surname = surnameField.getText();
//            String email = usernameField.getText();
//            char[] passwordChars = passwordField.getPassword();
//            String houseNumber = houseNumberField.getText();
//            String streetName = streetNameField.getText();
//            String cityName = cityNameField.getText();
//            String postCode = postCodeField.getText();
//            System.out.println(forename);
//            System.out.println(surname);
//            System.out.println(email);
//            System.out.println(new String(passwordChars));
//            System.out.println(houseNumber + " " + streetName + " " +
//                    cityName + " " + postCode);
//            AccountOperations accountOperations =
//                    new AccountOperations();
//            Account account =
//                    new Account(List.of(UserRole.CUSTOMER), email,
//                            passwordChars,
//                            forename, surname);
//            AddressOperations addressOperations = new AddressOperations();
//            Address address = new Address(account.getUserID(),
//                    Integer.parseInt(houseNumber), streetName, cityName,
//                    postCode);
//            System.out.println(accountOperations.saveAccountIntoDatabase(
//                    connection, account));
//            System.out.println(addressOperations.saveAddressIntoDatabase(
//                    connection, address));
//            // Secure disposal of the password
//            Arrays.fill(passwordChars, '\u0000');
//
//            // Closes current register view
//            dispose();
//            // Create and show the new LoginView JFrame
//            try {
//                LoginView loginView = new LoginView(connection);
//                loginView.setVisible(true);
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        });
//
//
//    private class alterStockScreen extends JPanel {
//        public alterStockScreen() {
//            setLayout(new BorderLayout());
//
//            // Content for Inventory Screen
//            add(new JLabel("Alter Stock Screen content", SwingConstants.CENTER));
//
//            // Back button to the initial screen
//            JButton backButton = new JButton("Back to Main Screen");
//            backButton.addActionListener(e -> cardLayout.show(cardPanel, "Initial"));
//
//            JPanel buttonPanel = new JPanel();
//            buttonPanel.add(backButton);
//            add(buttonPanel, BorderLayout.SOUTH);
//        }
//    }
}