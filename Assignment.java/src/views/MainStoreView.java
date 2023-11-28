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

public class MainStoreView extends JFrame {
    private JTable productsTable;
    private JTable packsTable;
    public MainStoreView(Connection connection) {
        this.setTitle("Store");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        panel.add(topPanel);
        panel.add(middlePanel);
        panel.add(bottomPanel);
        this.add(panel);

        // Set a layout manager for the panels
        panel.setLayout(new GridLayout(3, 1));
        topPanel.setLayout(new GridLayout(1, 5));
        middlePanel.setLayout(new GridLayout(4, 1));
        bottomPanel.setLayout(new GridLayout(1, 5));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        productsTable = new JTable(tableModel);
        tableModel.addColumn("");
    }
}
