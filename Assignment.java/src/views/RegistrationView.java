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
import java.util.Arrays;
import java.util.List;

public class RegistrationView extends JFrame {
    private JTextField forenameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField houseNumberField;
    private JTextField streetNameField;
    private JTextField cityNameField;
    private JTextField postCodeField;


    public RegistrationView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Register");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(5, 2));

        // Create JLabels for username and password
        JLabel forenameLabel = new JLabel("Forename:");
        JLabel surnameLabel = new JLabel("Surname");
        JLabel usernameLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel houseNumberLabel = new JLabel("House Number:");
        JLabel streetNameLabel = new JLabel("Street:");
        JLabel cityNameLabel = new JLabel("City:");
        JLabel postCodeLabel = new JLabel("Postcode:");

        // Create JTextFields for entering username and password
        forenameField = new JTextField(40);
        surnameField = new JTextField(40);
        emailField = new JTextField(40);
        passwordField = new JPasswordField(40);
        houseNumberField = new JTextField(40);
        streetNameField = new JTextField(40);
        cityNameField = new JTextField(40);
        postCodeField = new JTextField(40);

        // Create a JButton for the register action
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login Instead");

        // Add components to the panel
        panel.add(forenameLabel);
        panel.add(forenameField);
        panel.add(surnameLabel);
        panel.add(surnameField);
        panel.add(usernameLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(houseNumberLabel);
        panel.add(houseNumberField);
        panel.add(streetNameLabel);
        panel.add(streetNameField);
        panel.add(cityNameLabel);
        panel.add(cityNameField);
        panel.add(postCodeLabel);
        panel.add(postCodeField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(registerButton);

        // Create an ActionListener for the register button
        registerButton.addActionListener(e -> {
            String forename = forenameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            if (!email.matches("^[a-zA-Z0-9_+&*-]+" +
                    "(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+" +
                    "[a-zA-Z]{2,7}$")) {
                // Invalid email, tell user and return.
                emailField.setText("Invalid email.");
                return;
            }
            char[] passwordChars = passwordField.getPassword();
            String houseNumber = houseNumberField.getText();
            try {
                int houseNumberInt =
                        Integer.parseInt(houseNumberField.getText());
                // The input is a valid integer, you can use it
                // Now you can use the 'houseNumber' variable
            } catch (NumberFormatException nfe) {
                // The input is not a valid integer
                // Handle the case where the input is not a valid integer
                // For example, show an error message to the user
                houseNumberField.setText("Invalid house number");
                return;
            }
            String streetName = streetNameField.getText();
            String cityName = cityNameField.getText();
            String postCode = postCodeField.getText();
            System.out.println(forename);
            System.out.println(surname);
            System.out.println(email);
            System.out.println(new String(passwordChars));
            System.out.println(houseNumber + " " + streetName + " " +
                                                    cityName + " " + postCode);
            AccountOperations accountOperations =
                    new AccountOperations();
            Account account =
                    new Account(List.of(UserRole.CUSTOMER), email,
                            passwordChars,
                            forename, surname);
            AddressOperations addressOperations = new AddressOperations();
            Address address = new Address(account.getUserID(),
                    Integer.parseInt(houseNumber), streetName, cityName,
                                                                     postCode);
            System.out.println(accountOperations.saveAccountIntoDatabase(
                    connection, account));
            System.out.println(addressOperations.saveAddressIntoDatabase(
                    connection, address));
            // Secure disposal of the password
            Arrays.fill(passwordChars, '\u0000');

            // Closes current register view
            dispose();
            // Create and show the new LoginView JFrame
            try {
                LoginView loginView = new LoginView(connection);
                loginView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        loginButton.addActionListener(e -> {
            // Closes current register view
            dispose();
            // Create and show the new LoginView JFrame
            try {
                LoginView loginView = new LoginView(connection);
                loginView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
