package src.views;

import src.account.Account;
import src.account.Address;
import src.account.BankDetails;
import src.model.AccountOperations;
import src.model.AddressOperations;
import src.model.BankDetailsOperations;
import src.util.CurrentUserCache;
import src.util.HashedPasswordGenerator;
import src.util.UniqueUserIDGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class ProfileView extends JFrame {
    // Components
    private JTextField forename;
    private JTextField surname;
    private JTextField email_address;
    private JTextField password;
    private JTextField house_number;
    private JTextField street_name;
    private JTextField city_name;
    private JTextField postcode;


    public ProfileView(Connection connection) throws SQLException {
        // GUI Initialization
        this.setTitle("Customer Profile");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(10, 2, 10, 10));

        Account currentUser = CurrentUserCache.getLoggedInUser();

        AddressOperations addressOperations = new AddressOperations();

        Address address = addressOperations.getAddressByUserID(connection,
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

        JLabel passwordLabel = new JLabel("New Password:");
        password = new JTextField(20);

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

        JButton bankDetailsButton = new JButton("View Bank Details");
        bankDetailsButton.addActionListener(e -> {
            // Create and show the new RegistrationView JFrame
            try {
                BankDetailsView bankDetailsView =
                        new BankDetailsView(connection);
                bankDetailsView.setVisible(true);
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
        panel.add(passwordLabel);
        panel.add(password);
        panel.add(houseNumberLabel);
        panel.add(house_number);
        panel.add(roadNameLabel);
        panel.add(street_name);
        panel.add(cityNameLabel);
        panel.add(city_name);
        panel.add(postcodeLabel);
        panel.add(postcode);
        panel.add(mainStoreButton);
        panel.add(saveButton);
        panel.add(new JLabel());
        panel.add(bankDetailsButton);

    }

    private void updateDetails(Connection connection, String userID) throws SQLException {
        AccountOperations accountOperations = new AccountOperations();
        if (accountOperations.checkAccountInDatabase(connection,
                email_address.getText()) && !Objects.equals(CurrentUserCache.getLoggedInUser().getEmailAddress(), email_address.getText())) {
            email_address.setText("User with that email already exists");
            return;
        }

        // Update Account table
        String updateAccountSql = "UPDATE Accounts SET forename = ?, surname " +
                "= ?, email_address = ?, unique_password_hash = ? WHERE " +
                "userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAccountSql)) {
            pstmt.setString(1, forename.getText());
            pstmt.setString(2, surname.getText());
            if (!email_address.getText().matches("^[a-zA-Z0-9_+&*-]+" +
                    "(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+" +
                    "[a-zA-Z]{2,7}$")) {
                // Invalid email, tell user and return.
                email_address.setText("Invalid email.");
                return;
            }
            pstmt.setString(3, email_address.getText());
            pstmt.setString(4,
                    HashedPasswordGenerator.hashPassword(
                            password.getText().toCharArray(), userID));
            pstmt.setString(5, userID);

            pstmt.executeUpdate();
        }

        // Update Address table
        String updateAddressSql = "UPDATE Addresses SET house_number = ?, " +
                "street_name = ?, city_name = ?, postcode = ? WHERE userID" +
                " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateAddressSql)) {
            try {
                int houseNumberInt =
                        Integer.parseInt(house_number.getText());
                // The input is a valid integer, you can use it
                // Now you can use the 'houseNumber' variable
            } catch (NumberFormatException nfe) {
                // The input is not a valid integer
                // Handle the case where the input is not a valid integer
                // For example, show an error message to the user
                house_number.setText("Invalid house number");
                return;
            }
            pstmt.setInt(1, Integer.parseInt(house_number.getText()));
            pstmt.setString(2, street_name.getText());
            pstmt.setString(3, city_name.getText());
            pstmt.setString(4, postcode.getText());
            pstmt.setString(5, userID);

            pstmt.executeUpdate();
        }
    }
}
