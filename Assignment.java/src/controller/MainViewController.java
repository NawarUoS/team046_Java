package src.controller;

import src.account.Account;
import src.model.DatabaseConnectionHandler;
import src.util.CurrentUserCache;
import src.views.LoginView;
import src.views.MainStoreView;
import src.views.ManagerView;
import src.views.RegistrationView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainViewController {
    public static void run() {
        DatabaseConnectionHandler databaseConnectionHandler =
                new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                loginView = new LoginView(databaseConnectionHandler.getConnection());
                loginView.setVisible(true);
            } catch (Throwable t) {
                // Close connection if the database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
