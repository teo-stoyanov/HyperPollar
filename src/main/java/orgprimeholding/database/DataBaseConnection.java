package orgprimeholding.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private DataBaseConnection() {
    }

    private static Connection connection;

    public static Connection getInstance(String dbName, Properties properties) throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection("jdbc:" + properties.getProperty("database.driver") +
                    "://" + properties.getProperty("database.server") + "/" + dbName, properties.getProperty("db.user")
                    ,properties.getProperty("db.password"));
        }
        return connection;
    }
}
