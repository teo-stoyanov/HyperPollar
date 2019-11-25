package orgprimeholding.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private DataBaseConnection() {
    }

    private static Connection connection;

    public static void createConnection(String dbName,Properties properties) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName,properties);
    }

    public static Connection getConnection() {
        return connection;
    }
}
