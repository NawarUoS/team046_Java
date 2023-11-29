package src.controller;

import src.account.Account;
import src.model.DatabaseConnectionHandler;
import src.views.LoginView;
import src.views.MainStoreView;
import src.views.ManagerView;
import src.views.RegistrationView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MainViewController {
    private static Account currentUser;
    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler =
                new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                loginView =
                    new LoginView(databaseConnectionHandler.getConnection());
                loginView.setVisible(true);
                currentUser = loginView.getUserInfo();
            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
