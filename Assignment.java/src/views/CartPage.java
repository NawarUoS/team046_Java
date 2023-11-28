
package src.views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CartPage extends JFrame {
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JButton removeButton;

    private Connection connection;

    public CartPage(Connection connection) throws SQLException {
        this.setTitle("Shopping Cart");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    }





}





