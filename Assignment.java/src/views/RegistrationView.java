package src.views;

import src.account.Account;
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
    private JTextField usernameField;
    private JPasswordField passwordField;
    public RegistrationView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Register");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 250);

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

        // Create JTextFields for entering username and password
        forenameField = new JTextField(20);
        surnameField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create a JButton for the login action
        JButton registerButton = new JButton("Register");

        // Add components to the panel
        panel.add(forenameLabel);
        panel.add(forenameField);
        panel.add(surnameLabel);
        panel.add(surnameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(registerButton);

        // Create an ActionListener for the register button
        registerButton.addActionListener(e -> {
            String forename = forenameField.getText();
            String surname = surnameField.getText();
            String email = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            System.out.println(forename);
            System.out.println(surname);
            System.out.println(email);
            System.out.println(new String(passwordChars));
            AccountOperations accountOperations =
                    new AccountOperations();
            Account account =
                    new Account(List.of(UserRole.CUSTOMER), email,
                            passwordChars,
                            forename, surname);
            System.out.println(accountOperations.saveAccountIntoDatabase(
                    connection, account));
            // Secure disposal of the password
            Arrays.fill(passwordChars, '\u0000');
        });
    }
}
