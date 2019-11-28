package orgprimeholding.core;

import orgprimeholding.constants.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeDb {
    private InitializeDb() {
    }

    public static void createTables(Connection connection) throws SQLException {
        for (String query : SqlQueries.getAllQueries()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
        }
    }
}
