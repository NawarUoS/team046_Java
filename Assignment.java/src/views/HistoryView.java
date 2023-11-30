package src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryView extends JPanel {
    private Connection connection;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public HistoryView(
            Connection connection, CardLayout cardLayout, JPanel cardPanel) {
        this.connection = connection;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        // Content for History Screen
        add(new JLabel("History Screen Content", SwingConstants.CENTER));

        // Back button to the initial screen
        JButton backButton = new JButton("Back to Main Screen");
        backButton.addActionListener(
            e -> cardLayout.show(cardPanel, "Initial"));

        // Table with data from the SQL table
        JTable table = createTableFromSQL();
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createTableFromSQL() {
        String[] columnNames = {"Order Number", "Order Date", "Total Cost", 
                                "Order Status", "User ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // SQL query to retrieve data from your table
        String sqlQuery = "SELECT order_number, order_date, total_cost, " 
                        + "order_status, userID FROM Orders";

        try (PreparedStatement preparedStatement = 
                                    connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_number"),
                        resultSet.getDate("order_date"), // Adjust the data type if necessary
                        resultSet.getDouble("total_cost"),
                        resultSet.getString("order_status"),
                        resultSet.getString("userID")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(model);
    }
}
