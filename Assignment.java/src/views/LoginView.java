package src.views;

import src.account.Account;
import src.model.*;
import src.util.CurrentUserCache;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private String email;
    private Connection connection;
    private boolean loginSuccess;
    public LoginView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 150);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.connection = connection;

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(3, 2));

        // Create JLabels for username and password
        JLabel usernameLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create JTextFields for entering username and password
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create a JButton for the login action
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Sign Up");

        // Add components to the panel
        panel.add(usernameLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(loginButton);

        // Create an ActionListener for the login button
        loginButton.addActionListener(e -> {
            AccountOperations accountOperations = new AccountOperations();

            email = emailField.getText();
            if (!accountOperations.checkAccountInDatabase(connection, email)) {
                emailField.setText("User does not exist.");
                return;
            }

            char[] passwordChars = passwordField.getPassword();
            if (!LoginOperations.verifyLogin(connection, email,
                                                            passwordChars)) {
                emailField.setText("Incorrect password.");
                return;
            }
            System.out.println(email);
            System.out.println(new String(passwordChars));
            System.out.println(LoginOperations.verifyLogin(
                    connection, email, passwordChars));
            CurrentUserCache.storeUserInformation(
                    accountOperations.getAccountByEmail(connection, email));
            // Continue only if login is successful
            DatabaseConnectionHandler databaseConnectionHandler =
                    new DatabaseConnectionHandler();
            MainStoreView mainStoreView = null;
            if (LoginOperations.verifyLogin(connection, email, passwordChars))
                loginSuccess = true;
            if (getLoginSuccess()) {
                dispose();
                try {
                    // Open a new database connection
                    databaseConnectionHandler.openConnection();

                    mainStoreView =
                            new MainStoreView(databaseConnectionHandler.getConnection());
                    mainStoreView.setVisible(true);
                    System.out.println(
                            CurrentUserCache.getLoggedInUser().getForename());
                } catch (Throwable t) {
                    // Close connection if the database crashes.
                    databaseConnectionHandler.closeConnection();
                    throw new RuntimeException(t);
                }
            } else {
                System.out.println("NO");
                // TODO reset login view
            }
            // Secure disposal of the password
            Arrays.fill(passwordChars, '\u0000');
        });

        registerButton.addActionListener(e -> {
            // Closes current login view
            dispose();
            // Create and show the new RegistrationView JFrame
            try {
                RegistrationView registerView =
                        new RegistrationView(connection);
                registerView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean getLoginSuccess() {
        return loginSuccess;
    }
}
