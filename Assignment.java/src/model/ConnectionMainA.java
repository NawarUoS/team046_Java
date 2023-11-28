package src.model;

import src.views.LoginView;
import src.views.ManagerView;
import src.views.RegistrationView;
import src.views.StaffView;

import javax.swing.*;

public class ConnectionMainA {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler =
                new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            StaffView staffView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // test view
                staffView =
                    new StaffView(databaseConnectionHandler.getConnection());
                staffView.setVisible(true);
            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
