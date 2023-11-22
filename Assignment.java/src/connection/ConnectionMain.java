package src.connection;

import src.connection.*;
import src.views.LoginView;

import javax.swing.*;

public class ConnectionMain {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                loginView = new LoginView(databaseConnectionHandler.getConnection());
                loginView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}