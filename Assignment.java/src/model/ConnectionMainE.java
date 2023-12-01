package src.model;

import src.views.*;

import javax.swing.*;

public class ConnectionMainE {
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
                InventoryView inventoryView =
                        new InventoryView(databaseConnectionHandler.getConnection());
                inventoryView.setVisible(true);


            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
