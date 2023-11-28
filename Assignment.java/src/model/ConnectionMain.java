package src.model;

import src.views.*;

import javax.swing.*;

public class ConnectionMain {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler =
                new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RegistrationView registrationView = null;
            LoginView loginView = null;
            ManagerView managerView = null;
            StaffView staffView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // test registration
                registrationView =
                new RegistrationView(databaseConnectionHandler.getConnection());
                registrationView.setVisible(false);

                // test login
                loginView =
                    new LoginView(databaseConnectionHandler.getConnection());
                loginView.setVisible(false);

                // test manager
                managerView =
                    new ManagerView(databaseConnectionHandler.getConnection());
                managerView.setVisible(false);
                
                // test staff 
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