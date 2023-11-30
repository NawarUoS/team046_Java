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
    private JTextField email_address;
    private JTextField house_number;
    private JTextField street_name;
    private JTextField city_name;
    private JTextField postcode;
    private JTextField card_company_name;
    private JTextField card_number;
    private JTextField card_name;
    private JTextField expiry_date;
    private JTextField security_code;

    public ProfileView(Connection connection) throws SQLException {
        // GUI Initialization
        this.setTitle("Customer Profile");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(13, 2, 10, 10));

        Account currentUser = CurrentUserCache.getLoggedInUser();

        AddressOperations addressOperations = new AddressOperations();
        BankDetailsOperations bankDetailsOperations =
                new BankDetailsOperations();

        Address address = addressOperations.getAddressByUserID(connection,
                        currentUser.getUserID());

        boolean hasBankDetails =
                bankDetailsOperations.checkBankDetailsInDatabase(connection,
                        currentUser.getUserID());
        BankDetails bankDetails =
                bankDetailsOperations.getBankDetailsByUserID(connection,
                            currentUser.getUserID());

        // Create Labels and Text Fields
        JLabel firstNameLabel = new JLabel("First Name:");
        forename = new JTextField(20);
        forename.setText(currentUser.getForename());

        JLabel lastNameLabel = new JLabel("Last Name:");
        surname = new JTextField(20);
        surname.setText(currentUser.getSurname());

        JLabel emailLabel = new JLabel("Email:");
        email_address = new JTextField(20);
        email_address.setText(currentUser.getEmailAddress());

        JLabel houseNumberLabel = new JLabel("House Number:");
        house_number = new JTextField(20);
        house_number.setText(String.valueOf(address.getHouseNumber()));

        JLabel roadNameLabel = new JLabel("Road Name:");
        street_name = new JTextField(20);
        street_name.setText(address.getStreetName());

        JLabel cityNameLabel = new JLabel("City Name:");
        city_name = new JTextField(20);
        city_name.setText(address.getCityName());

        JLabel postcodeLabel = new JLabel("Postcode:");
        postcode = new JTextField(20);
        postcode.setText(address.getPostCode());

        JLabel cardNameLabel = new JLabel("Card Name: ");
        card_company_name = new JTextField(20);
        card_company_name.setText(hasBankDetails ? bankDetails.getCardName()
                : "");

        JLabel cardNumberLabel = new JLabel("Card Number:");
        card_number = new JTextField(20);
        card_number.setText(hasBankDetails ?
                String.valueOf(bankDetails.getCardNumber()) :
                "0000000000000000");

        JLabel cardHolderLabel = new JLabel("Cardholder Name:");
        card_name = new JTextField(20);
        card_name.setText(hasBankDetails ? bankDetails.getCardHolder() :
                "");

        JLabel cardExpiryLabel = new JLabel("Card Expiry (mm/yy):");
        expiry_date = new JTextField(20);
        expiry_date.setText(hasBankDetails ? bankDetails.getExpiryDate() :
                "00/00");

        JLabel securityCodeLabel = new JLabel("Security Code:");
        security_code = new JTextField(20);
        security_code.setText(hasBankDetails ?
                String.valueOf(bankDetails.getSecurityCode()) : "000");

        // Button to Save Changes
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                updateDetails(connection, currentUser.getUserID());
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle any SQL exceptions here
            }
        });

        JButton mainStoreButton = new JButton("Main Store Page");
        mainStoreButton.addActionListener(e -> {
            // Closes current login view
            dispose();
            // Create and show the new RegistrationView JFrame
            try {
                MainStoreView mainStoreView =
                        new MainStoreView(connection);
                mainStoreView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });


        // Add components to the frame
        panel.add(firstNameLabel);
        panel.add(forename);
        panel.add(lastNameLabel);
        panel.add(surname);
        panel.add(emailLabel);
        panel.add(email_address);
        panel.add(houseNumberLabel);
        panel.add(house_number);
        panel.add(roadNameLabel);
        panel.add(street_name);
        panel.add(cityNameLabel);
        panel.add(city_name);
        panel.add(postcodeLabel);
        panel.add(postcode);
        panel.add(cardNameLabel);
        panel.add(card_company_name);
        panel.add(cardNumberLabel);
        panel.add(card_number);
        panel.add(cardHolderLabel);
        panel.add(card_name);
        panel.add(cardExpiryLabel);
        panel.add(expiry_date);
        panel.add(securityCodeLabel);
        panel.add(security_code);
        panel.add(mainStoreButton);
        panel.add(saveButton);

    }

    private void updateDetails(Connection connection, String userID) throws SQLException {

        // Update Account table
        String updateAccountSql = "UPDATE Accounts SET forename = ?, surname " +
                "= ?, email_address = ? WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAccountSql)) {
            pstmt.setString(1, forename.getText());
            pstmt.setString(2, surname.getText());
            pstmt.setString(3, email_address.getText());
            pstmt.setString(4, userID);

            pstmt.executeUpdate();
        }

        // Update Address table
        String updateAddressSql = "UPDATE Addresses SET house_number = ?, " +
                "street_name = ?, city_name = ?, postcode = ? WHERE userID" +
                " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAddressSql)) {
            pstmt.setInt(1, Integer.parseInt(house_number.getText()));
            pstmt.setString(2, street_name.getText());
            pstmt.setString(3, city_name.getText());
            pstmt.setString(4, postcode.getText());
            pstmt.setString(5, userID);

            pstmt.executeUpdate();
        }

        BankDetailsOperations bankDetailsOperations =
                new BankDetailsOperations();

        if (bankDetailsOperations.checkBankDetailsInDatabase(connection,
                userID)) {
            // Update BankDetails table
            String updateBankDetailsSql = "UPDATE BankDetails SET " +
                    "card_company_name" +
                    " " +
                    "=" +
                    " " +
                    "?," +
                    " " +
                    "card_name = ?, card_number = ?, expiry_date = ?, " +
                    "security_code" +
                    " " +
                    "=" +
                    " " +
                    "?" +
                    " " +
                    "WHERE userID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateBankDetailsSql)) {
                pstmt.setString(1, card_company_name.getText());
                pstmt.setString(2, card_name.getText());
                pstmt.setLong(3, Long.parseLong(card_number.getText()));
                pstmt.setString(4, expiry_date.getText());
                pstmt.setInt(5, Integer.parseInt(
                        security_code.getText()));
                pstmt.setString(6, userID);

                pstmt.executeUpdate();
            }
        } else {
            BankDetails bankDetails = new BankDetails(
                    card_company_name.getText(),
                    card_name.getText(),
                    Long.parseLong(card_number.getText()),
                    expiry_date.getText(),
                    Integer.parseInt(security_code.getText()),
                    CurrentUserCache.getLoggedInUser().getUserID());
            bankDetailsOperations.saveBankDetailsIntoDatabase(
                    connection, bankDetails);
        }

    }

}
