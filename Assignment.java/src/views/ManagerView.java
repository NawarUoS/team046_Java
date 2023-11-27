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

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(4, 3));

        // Create JLabels for username and password
        JLabel promoteLabel = new JLabel("Promote Users to Staff:");
        JLabel dismissLabel = new JLabel("Dismiss User from Staff:");

        // Create JTextFields for entering username and password
        userPromote1Field = new JTextField(40);
        userPromote2Field = new JTextField(40);
        userDismiss1Field = new JTextField(40);

        // Create a JButton for the promote and dismiss actions
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
        tableModel.addColumn("Email");
        tableModel.addColumn("Forename");
        tableModel.addColumn("Surname");


        // Populate the JTable with query results
        DatabaseConnectionHandler connectionHandler =
                new DatabaseConnectionHandler();
        AccountOperations accountOperations = new AccountOperations();
        ResultSet resultSet =
                accountOperations.getStaff(connection);
        // Populate the JTable with the query results
        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getString("forename"),
                    resultSet.getString("surname"),
                    resultSet.getString("email_address")
            });
        }

        resultSet.close();
        connection.close();
        // Create a JScrollPane to display the table
        JScrollPane scrollPane = new JScrollPane(staffTable);

        // Add components to the panel
        panel.add(promoteLabel);
        panel.add(userPromote1Field);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(userPromote2Field);
        panel.add(scrollPane);
        panel.add(dismissLabel);
        panel.add(userDismiss1Field);
        panel.add(new JLabel());

        // Add buttons
        panel.add(promoteUsersButton);
        panel.add(dismissUserButton);

        // Create an ActionListener for the promote users button
        promoteUsersButton.addActionListener(e -> {
            String userPromote1 = userPromote1Field.getText();
            String userPromote2 = userPromote2Field.getText();

        });

        // Create an ActionListener for the dismiss user button
        dismissUserButton.addActionListener(e -> {
            String userDismiss1 = userDismiss1Field.getText();
        });
    }
}
