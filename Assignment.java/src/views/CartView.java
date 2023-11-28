package src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class CartView extends JFrame {
    // Components
    private JList<String> cartItemList;
    private JButton checkoutButton;
    private JButton removeItemButton;

    public CartView(Connection connection) throws SQLException {
        // GUI Initialization
        this.setTitle("Shopping Cart");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(12, 2, 10, 10));

        // Create a list to display cart items
        DefaultListModel<String> cartListModel = new DefaultListModel<>();
        cartItemList = new JList<>(cartListModel);
        JScrollPane scrollPane = new JScrollPane(cartItemList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        checkoutButton = new JButton("Checkout");
        removeItemButton = new JButton("Remove Item");
        buttonPanel.add(removeItemButton);
        buttonPanel.add(checkoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Add a method to update the cart items in the JList
    public void updateCartItems(String[] items) {
        DefaultListModel<String> cartListModel = (DefaultListModel<String>) cartItemList.getModel();
        cartListModel.clear();
        for (String item : items) {
            cartListModel.addElement(item);
        }
    }

    // Add action listeners for checkout and remove item buttons

    public void addCheckoutButtonListener(ActionListener listener) {
        checkoutButton.addActionListener(listener);
    }

    public void addRemoveItemButtonListener(ActionListener listener) {
        removeItemButton.addActionListener(listener);
    }
}







