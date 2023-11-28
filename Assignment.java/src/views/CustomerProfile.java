package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class CustomerProfile extends JFrame {
        // Components
        private JTextField firstNameField, lastNameField, emailField, addressField, cardNumberField, cardHolderField, cardExpiryField, securityCodeField;

        public CustomerProfile(Connection connection)  throws SQLException {
            // GUI Initialization
            this.setTitle("Customer Profile");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new GridLayout(8, 2, 10, 10));
            setPreferredSize(new Dimension(400, 400));


            // Create Labels and Text Fields
            JLabel firstNameLabel = new JLabel("First Name:");
            firstNameField = new JTextField(20);

            JLabel lastNameLabel = new JLabel("Last Name:");
            lastNameField = new JTextField(20);

            JLabel emailLabel = new JLabel("Email:");
            emailField = new JTextField(20);

            JLabel addressLabel = new JLabel("Address:");
            addressField = new JTextField(20);

            JLabel cardNumberLabel = new JLabel("Card Number:");
            cardNumberField = new JTextField(20);

            JLabel cardHolderLabel = new JLabel("Cardholder Name:");
            cardHolderField = new JTextField(20);

            JLabel cardExpiryLabel = new JLabel("Card Expiry (mm/yy):");
            cardExpiryField = new JTextField(20);

            JLabel securityCodeLabel = new JLabel("Security Code:");
            securityCodeField = new JTextField(20);

            // Button to Save Changes
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call method to update details in the database
                    updateDetails();
                }
            });

            // Add components to the frame
            add(firstNameLabel);
            add(firstNameField);
            add(lastNameLabel);
            add(lastNameField);
            add(emailLabel);
            add(emailField);
            add(addressLabel);
            add(addressField);
            add(cardNumberLabel);
            add(cardNumberField);
            add(cardHolderLabel);
            add(cardHolderField);
            add(cardExpiryLabel);
            add(cardExpiryField);
            add(securityCodeLabel);
            add(securityCodeField);
            add(saveButton);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        // Method to update customer details in the database
        private void updateDetails() {
            String fname = firstNameField.getText();
            String lname = lastNameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String cardNumber = cardNumberField.getText();
            String cardHolder = cardHolderField.getText();
            String cardExpiry = cardExpiryField.getText();
            String securityCode = securityCodeField.getText();

            // Database connection and update
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "username", "password");
                String sql = "UPDATE something SET fname=?, lname=?, email=?, address=?, card_number=?, card_holder=?, card_expiry=?, security_code=? WHERE something=?";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set parameters
                statement.setString(1, fname);
                statement.setString(1, lname);
                statement.setString(2, email);
                statement.setString(3, address);
                statement.setString(4, cardNumber);
                statement.setString(5, cardHolder);
                statement.setString(6, cardExpiry);
                statement.setString(7, securityCode);
                // Set customer_id based on logged-in user or other identifier

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update details.");
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


