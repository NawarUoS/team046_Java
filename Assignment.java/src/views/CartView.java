




package src.views;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JFrame {
    // Components
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField houseNumberField;
    private JTextField roadNameField;
    private JTextField cityNameField;
    private JTextField postcodeField;
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField cardExpiryField;
    private JTextField securityCodeField;

    public ProfileView() {
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

        JLabel houseNumberLabel = new JLabel("House Number:");
        houseNumberField = new JTextField(20);

        JLabel roadNameLabel = new JLabel("Road Name:");
        roadNameField = new JTextField(20);

        JLabel cityNameLabel = new JLabel("City Name:");
        cityNameField = new JTextField(20);

        JLabel postcodeLabel = new JLabel("Postcode:");
        postcodeField = new JTextField(20);

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
        panel.add(houseNumberLabel);
        panel.add(houseNumberField);
        panel.add(roadNameLabel);
        panel.add(roadNameField);
        panel.add(cityNameLabel);
        panel.add(cityNameField);
        panel.add(postcodeLabel);
        panel.add(postcodeField);
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






