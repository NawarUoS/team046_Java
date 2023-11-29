package src.views;

import src.account.Account;
import src.account.Address;
import src.account.BankDetails;
import src.model.AddressOperations;
import src.model.BankDetailsOperations;
import src.util.CurrentUserCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileView extends JFrame {
    // Components
    private JTextField forename;
    private JTextField surname;
    private JTextField emailAddress;
    private JTextField houseNumber;
    private JTextField roadName;
    private JTextField cityName;
    private JTextField postCode;
    private JTextField cardName;
    private JTextField cardNumber;
    private JTextField cardHolder;
    private JTextField expiryDate;
    private JTextField securityCode;

    public ProfileView(Connection connection) throws SQLException {
        // GUI Initialization
        this.setTitle("Customer Profile");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(12, 2, 10, 10));

        Account currentUser = CurrentUserCache.getLoggedInUser();

        AddressOperations addressOperations = new AddressOperations();
        BankDetailsOperations bankDetailsOperations =
                new BankDetailsOperations();

        Address address = addressOperations.getAddressByUserID(connection,
                        currentUser.getUserID());

        BankDetails bankDetails;
        if (bankDetailsOperations.checkBankDetailsInDatabase(connection,
                currentUser.getUserID())) {
            bankDetails =
                    bankDetailsOperations.getBankDetailsByUserID(connection,
                            currentUser.getUserID());
        } else {
            bankDetails = new BankDetails("N/A", "N/A", 0000000000000000, "00" +
                    "-00-0000", 000);
        }
        // Create Labels and Text Fields
        JLabel firstNameLabel = new JLabel("First Name:");
        forename = new JTextField(20);
        forename.setText(currentUser.getForename());

        JLabel lastNameLabel = new JLabel("Last Name:");
        surname = new JTextField(20);
        surname.setText(currentUser.getSurname());

        JLabel emailLabel = new JLabel("Email:");
        emailAddress = new JTextField(20);
        emailAddress.setText(currentUser.getEmailAddress());

        JLabel houseNumberLabel = new JLabel("House Number:");
        houseNumber = new JTextField(20);
        houseNumber.setText(String.valueOf(address.getHouseNumber()));

        JLabel roadNameLabel = new JLabel("Road Name:");
        roadName = new JTextField(20);
        roadName.setText(address.getStreetName());

        JLabel cityNameLabel = new JLabel("City Name:");
        cityName = new JTextField(20);
        cityName.setText(address.getCityName());

        JLabel postcodeLabel = new JLabel("Postcode:");
        postCode = new JTextField(20);
        postCode.setText(address.getPostCode());

        JLabel cardNameLabel = new JLabel("Card Name: ");
        cardName = new JTextField(20);
        cardName.setText(bankDetails.getCardName());

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumber = new JTextField(20);
        cardNumber.setText(String.valueOf(bankDetails.getCardNumber()));

        JLabel cardHolderLabel = new JLabel("Cardholder Name:");
        cardHolder = new JTextField(20);
        cardHolder.setText(bankDetails.getCardHolder());

        JLabel cardExpiryLabel = new JLabel("Card Expiry (mm/yy):");
        expiryDate = new JTextField(20);
        expiryDate.setText(bankDetails.getExpiryDate());

        JLabel securityCodeLabel = new JLabel("Security Code:");
        securityCode = new JTextField(20);
        securityCode.setText(String.valueOf(bankDetails.getSecurityCode()));

        // Button to Save Changes
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateDetails(connection, currentUser.getUserID());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle any SQL exceptions here
                }
            }
        });


        // Add components to the frame
        panel.add(firstNameLabel);
        panel.add(forename);
        panel.add(lastNameLabel);
        panel.add(surname);
        panel.add(emailLabel);
        panel.add(emailAddress);
        panel.add(houseNumberLabel);
        panel.add(houseNumber);
        panel.add(roadNameLabel);
        panel.add(roadName);
        panel.add(cityNameLabel);
        panel.add(cityName);
        panel.add(postcodeLabel);
        panel.add(postCode);
        panel.add(cardNameLabel);
        panel.add(cardName);
        panel.add(cardNumberLabel);
        panel.add(cardNumber);
        panel.add(cardHolderLabel);
        panel.add(cardHolder);
        panel.add(cardExpiryLabel);
        panel.add(expiryDate);
        panel.add(securityCodeLabel);
        panel.add(securityCode);
        panel.add(saveButton);

    }

    private void updateDetails(Connection connection, String userID) throws SQLException {

        // Update Account table
        String updateAccountSql = "UPDATE Accounts SET forename = ?, surname " +
                "= ?, emailAddress = ? WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAccountSql)) {
            pstmt.setString(1, forename.getText());
            pstmt.setString(2, surname.getText());
            pstmt.setString(3, emailAddress.getText());
            pstmt.setString(4, userID);

            pstmt.executeUpdate();
        }

        // Update Address table
        String updateAddressSql = "UPDATE Addresses SET houseNumber = ?, " +
                "roadName = ?, cityName = ?, postCode = ? WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAddressSql)) {
            pstmt.setInt(1, Integer.parseInt(houseNumber.getText()));
            pstmt.setString(2, roadName.getText());
            pstmt.setString(3, cityName.getText());
            pstmt.setString(4, postCode.getText());
            pstmt.setString(5, userID);

            pstmt.executeUpdate();
        }

        // Update BankDetails table
        String updateBankDetailsSql = "UPDATE BankDetails SET cardName = ?, cardHolder = ?, cardNumber = ?, expiryDate = ?, securityCode = ? WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateBankDetailsSql)) {
            pstmt.setString(1, cardName.getText());
            pstmt.setString(2, cardHolder.getText());
            pstmt.setInt(3, Integer.parseInt(cardNumber.getText()));
            pstmt.setString(4, expiryDate.getText());
            pstmt.setInt(5, Integer.parseInt(securityCode.getText()));
            pstmt.setString(6, userID);

            pstmt.executeUpdate();
        }

    }

}
