package src.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {

    // Get All books over the due date
    public ResultSet getBooksOverDueDate(Connection connection) throws SQLException {
        ResultSet resultSet = null;
        try {
            // Establish a database connection

            // Execute the SQL query
            String sqlQuery = "SELECT *";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
