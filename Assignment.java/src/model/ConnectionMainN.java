package src.model;

import com.mysql.cj.log.Log;
import src.views.LoginView;
import src.views.MainStoreView;
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
            MainStoreView mainStoreView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // test view
                mainStoreView =
                    new MainStoreView(databaseConnectionHandler.getConnection());
                mainStoreView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
