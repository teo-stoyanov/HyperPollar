package orgprimeholding.core;

import orgprimeholding.constants.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDb {
    private CreateDb() {
    }

    public static void createMyDb(Connection connection) throws SQLException {
        for (String query : SqlQueries.getAllQueries()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
        }
    }
}
