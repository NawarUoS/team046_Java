package src.connection;

import javax.swing.*;

public class ConnectionMain {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnectionHandler for managing database connections
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

            } catch (Throwable t) {
                throw new RuntimeException(t);
            } finally {
                // Close the database connection
                databaseConnectionHandler.closeConnection();
            }

        });
    }
}
