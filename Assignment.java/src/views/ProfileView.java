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
    private JTextField road_name;
    private JTextField city_name;
    private JTextField post_code;
    private JTextField card_name;
    private JTextField card_number;
    private JTextField card_holder;
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
        email_address = new JTextField(20);
        email_address.setText(currentUser.getEmailAddress());

        JLabel houseNumberLabel = new JLabel("House Number:");
        house_number = new JTextField(20);
        house_number.setText(String.valueOf(address.getHouseNumber()));

        JLabel roadNameLabel = new JLabel("Road Name:");
        road_name = new JTextField(20);
        road_name.setText(address.getStreetName());

        JLabel cityNameLabel = new JLabel("City Name:");
        city_name = new JTextField(20);
        city_name.setText(address.getCityName());

        JLabel postcodeLabel = new JLabel("Postcode:");
        post_code = new JTextField(20);
        post_code.setText(address.getPostCode());

        JLabel cardNameLabel = new JLabel("Card Name: ");
        card_name = new JTextField(20);
        card_name.setText(bankDetails.getCardName());

        JLabel cardNumberLabel = new JLabel("Card Number:");
        card_number = new JTextField(20);
        card_number.setText(String.valueOf(bankDetails.getCardNumber()));

        JLabel cardHolderLabel = new JLabel("Cardholder Name:");
        card_holder = new JTextField(20);
        card_holder.setText(bankDetails.getCardHolder());

        JLabel cardExpiryLabel = new JLabel("Card Expiry (mm/yy):");
        expiry_date = new JTextField(20);
        expiry_date.setText(bankDetails.getExpiryDate());

        JLabel securityCodeLabel = new JLabel("Security Code:");
        security_code = new JTextField(20);
        security_code.setText(String.valueOf(bankDetails.getSecurityCode()));

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
        panel.add(email_address);
        panel.add(houseNumberLabel);
        panel.add(house_number);
        panel.add(roadNameLabel);
        panel.add(road_name);
        panel.add(cityNameLabel);
        panel.add(city_name);
        panel.add(postcodeLabel);
        panel.add(post_code);
        panel.add(cardNameLabel);
        panel.add(card_name);
        panel.add(cardNumberLabel);
        panel.add(card_number);
        panel.add(cardHolderLabel);
        panel.add(card_holder);
        panel.add(cardExpiryLabel);
        panel.add(expiry_date);
        panel.add(securityCodeLabel);
        panel.add(security_code);
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
                "road_number = ?, city_number = ?, post_code = ? WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAddressSql)) {
            pstmt.setInt(1, Integer.parseInt(house_number.getText()));
            pstmt.setString(2, road_name.getText());
            pstmt.setString(3, city_name.getText());
            pstmt.setString(4, post_code.getText());
            pstmt.setString(5, userID);

            pstmt.executeUpdate();
        }

        // Update BankDetails table
        String updateBankDetailsSql = "UPDATE BankDetails SET card_name = ?, card_holder = ?, card_number = ?, expiry_date = ?, security_code = ? WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateBankDetailsSql)) {
            pstmt.setString(1, card_name.getText());
            pstmt.setString(2, card_holder.getText());
            pstmt.setInt(3, Integer.parseInt(card_number.getText()));
            pstmt.setString(4, expiry_date.getText());
            pstmt.setInt(5, Integer.parseInt(security_code.getText()));
            pstmt.setString(6, userID);

            pstmt.executeUpdate();
        }

    }

}
