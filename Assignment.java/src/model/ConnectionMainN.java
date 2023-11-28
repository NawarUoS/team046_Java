package src.model;

import src.views.LoginView;
import src.views.ManagerView;
import src.views.RegistrationView;

import javax.swing.*;

public class ConnectionMainN {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler =
                new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RegistrationView registrationView = null;
            LoginView loginView = null;
            ManagerView managerView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // test view
                managerView =
                        new ManagerView(databaseConnectionHandler.getConnection());
                managerView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
