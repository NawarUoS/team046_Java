package src.views;

import src.account.*;
import src.model.*;
import src.model.*;
import src.util.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ManagerView extends JFrame {
    private JTextField userPromote1Field;
    private JTextField userPromote2Field;
    private JTextField userDismiss1Field;
    private JTable staffTable;

    public ManagerView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Manager Dashboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        panel.add(leftPanel);
        panel.add(rightPanel);
        this.add(panel);

        // Set a layout manager for the panels
        panel.setLayout(new GridLayout(1, 2));
        leftPanel.setLayout(new GridLayout(9, 1));
        rightPanel.setLayout(new GridLayout(1, 1));

        // Create JLabels for username and password
        JLabel promoteLabel = new JLabel("Promote Users to Staff:");
        JLabel dismissLabel = new JLabel("Dismiss User from Staff:");

        // Create JTextFields for entering username and password
        userPromote1Field = new JTextField(40);
        userPromote2Field = new JTextField(40);
        userDismiss1Field = new JTextField(40);

        // Create a JButton for the promote and dismiss actions
        JButton mainStoreButton = new JButton("Staff View");
        JButton promoteUsersButton = new JButton("Promote Users");
        JButton dismissUserButton = new JButton("Dismiss User");

        // Create a DefaultTableModel for JTable
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        staffTable = new JTable(tableModel);
        tableModel.addColumn("Forename");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Email");


        // Populate the JTable with query results
        ManagerOperations managerOperations = new ManagerOperations();
        ResultSet resultSet =
                managerOperations.getStaff(connection);
        // Populate the JTable with the query results
        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getString("forename"),
                    resultSet.getString("surname"),
                    resultSet.getString("email_address")
            });
        }

        // Create a JScrollPane to display the table
        JScrollPane scrollPane = new JScrollPane(staffTable);

        // Add components to the left panel
        leftPanel.add(mainStoreButton);
        leftPanel.add(promoteLabel);
        leftPanel.add(userPromote1Field);
        leftPanel.add(userPromote2Field);
        leftPanel.add(promoteUsersButton);
        leftPanel.add(new JLabel());
        leftPanel.add(dismissLabel);
        leftPanel.add(userDismiss1Field);
        leftPanel.add(dismissUserButton);

        // Add table
        rightPanel.add(scrollPane);

        // Combine it all in main panel
        panel.add(leftPanel);
        panel.add(rightPanel);

        // Create an ActionListener for the main store view button
        mainStoreButton.addActionListener(e -> {
            dispose();
            try {
                StaffView staffView = new StaffView(connection);
                staffView.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Create an ActionListener for the promote users button
        promoteUsersButton.addActionListener(e -> {
            String userPromote1 = userPromote1Field.getText();
            String userPromote2 = userPromote2Field.getText();

            List<String> usersToPromote = List.of(userPromote1, userPromote2);
            System.out.println(managerOperations.promoteUsersToStaff(
                    connection, usersToPromote));
        });

        // Create an ActionListener for the dismiss user button
        dismissUserButton.addActionListener(e -> {
            String userDismiss1 = userDismiss1Field.getText();

            System.out.println(managerOperations.dismissUserFromStaff(
                    connection, userDismiss1));
        });
    }
}
