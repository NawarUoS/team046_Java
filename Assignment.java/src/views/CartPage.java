package src.views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CartPage extends JFrame {
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JButton removeButton;

    private Connection connection;

    public CartPage() {
        setTitle("Shopping Cart");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        JScrollPane scrollPane = new JScrollPane(cartList);
        add(scrollPane, BorderLayout.CENTER);

        removeButton = new JButton("Remove Item");
        removeButton.addActionListener(e -> {
            int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                cartListModel.remove(selectedIndex);
            }
        });
        add(removeButton, BorderLayout.SOUTH);

        connectToDatabase();
        fetchItemsFromDatabase();
    }

    // Method to establish a database connection
    private void connectToDatabase() {
        try {
            // Replace these with your database credentials
            String url = "jdbc:mysql://localhost:3306/your_database";
            String username = "your_username";
            String password = "your_password";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch items from the database and populate the cart
    private void fetchItemsFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT item_name FROM items");

            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                cartListModel.addElement(itemName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

