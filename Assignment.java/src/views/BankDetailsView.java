package src.views;

import src.account.Account;
import src.account.Address;
import src.account.BankDetails;
import src.model.AccountOperations;
import src.model.AddressOperations;
import src.model.BankDetailsOperations;
import src.util.CurrentUserCache;
import src.util.HashedBankDetailsGenerator;
import src.util.UniqueUserIDGenerator;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class BankDetailsView extends JFrame {
    private JTextField card_company_name;
    private JTextField card_number;
    private JTextField card_name;
    private JTextField expiry_date;
    private JTextField security_code;

    public BankDetailsView(Connection connection) throws SQLException {
        // GUI Initialization
        this.setTitle("Bank Details");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setLocation(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() /4,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() /4);


        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(6, 2, 10, 10));

        Account currentUser = CurrentUserCache.getLoggedInUser();

        BankDetailsOperations bankDetailsOperations =
                new BankDetailsOperations();

        // Create Labels and Text Fields
        JLabel cardNameLabel =
                new JLabel("Card Name (e.g VISA, MasterCard): ");
        card_company_name = new JTextField(20);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        card_number = new JTextField(20);

        JLabel cardHolderLabel = new JLabel("Cardholder Name:");
        card_name = new JTextField(20);

        JLabel cardExpiryLabel = new JLabel("Expiry Date (mm/yy):");
        expiry_date = new JTextField(20);

        JLabel securityCodeLabel = new JLabel("Security Code:");
        security_code = new JTextField(20);

        // Button to Save Changes
        JButton saveButton = new JButton("Save Bank Details");
        saveButton.addActionListener(e -> {
            if (bankDetailsOperations.checkBankDetailsInDatabase(connection,
                    currentUser.getUserID())) {
                // Update BankDetails table
                String updateBankDetailsSql = "UPDATE BankDetails SET " +
                    "card_company_name = ?, card_name = ?, card_number_hash =" +
                        " ?, expiry_date_hash = ? WHERE userID = ?";
                try (PreparedStatement pstmt =
                         connection.prepareStatement(updateBankDetailsSql)) {
                    pstmt.setString(1,
                                                card_company_name.getText());
                    pstmt.setString(2, card_name.getText());
                    pstmt.setString(3,
                            HashedBankDetailsGenerator.hashBankDetail(
                            card_number.getText(), currentUser.getUserID()));
                    pstmt.setString(4,
                            HashedBankDetailsGenerator.hashBankDetail(
                            expiry_date.getText(), currentUser.getUserID()));
                    pstmt.setString(5, currentUser.getUserID());

                    // Execute the insert statement
                    pstmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                BankDetails bankDetails =
                    new BankDetails(card_company_name.getText(),
                    card_name.getText(), Long.parseLong(card_number.getText()),
                    expiry_date.getText(), currentUser.getUserID());
                    bankDetailsOperations.saveBankDetailsIntoDatabase(
                                connection, bankDetails);
            }
        });

        JButton returnButton = new JButton("Return");

        // Add components to the frame
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
        panel.add(returnButton);
        panel.add(saveButton);

        returnButton.addActionListener(e -> {
            // Closes current login view
            dispose();
        });

    }
}
