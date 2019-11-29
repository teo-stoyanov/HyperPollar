package orgprimeholding.core;

import orgprimeholding.constants.SqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitializeDb {
    private static final Logger LOGGER = Logger.getLogger(InitializeDb.class.getName());

    private InitializeDb() {
    }

    public static void createTables(Connection connection) {
        for (String query : SqlQueries.getAllQueries()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.execute();

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
