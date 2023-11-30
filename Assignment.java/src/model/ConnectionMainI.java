package src.model;

import src.views.*;

import javax.swing.*;

public class ConnectionMainI {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler =
                new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            RegistrationView registrationView = null;
            LoginView loginView = null;
            ManagerView managerView = null;
            CartView cartView = null;
            ProfileView profileView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // test cart
                cartView =
                        new CartView(databaseConnectionHandler.getConnection());
                cartView.setVisible(false);

                // test profile
                profileView =
                        new ProfileView(databaseConnectionHandler.getConnection());
                profileView.setVisible(true);


            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
