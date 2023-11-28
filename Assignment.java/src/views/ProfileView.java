package src.views;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ProfileView extends JFrame {
    // Components
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField cardExpiryField;
    private JTextField securityCodeField;

    public ProfileView(Connection connection) throws SQLException {
        // GUI Initialization
        this.setTitle("Customer Profile");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(12, 2, 10, 10));

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
//            saveButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    // Call method to update details in the database
//                    updateDetails();
//                }
//            });

        // Add components to the frame
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(cardHolderLabel);
        panel.add(cardHolderField);
        panel.add(cardExpiryLabel);
        panel.add(cardExpiryField);
        panel.add(securityCodeLabel);
        panel.add(securityCodeField);
        panel.add(saveButton);

    }

}