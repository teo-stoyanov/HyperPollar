package orgprimeholding.repository;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseRepository<T> {
    private static final Logger LOGGER = Logger.getLogger(BaseRepository.class.getName());
    private static final String REPLACE = "_entity";
    private static final String REPLACEMENT = "";

    private Class<T> entity;
    private Connection connection;

    BaseRepository(Class<T> entity, Connection connection) {
        this.entity = entity;
        this.connection = connection;
    }

    String insertQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(toSnakeCase(this.entity.getSimpleName()).replace(REPLACE, REPLACEMENT)).append(" (");
        String[] fieldsName = Arrays.stream(this.entity.getDeclaredFields())
                .filter(f ->f.isAnnotationPresent(Column.class) && !f.getName().equalsIgnoreCase("id"))
                .map(Field::getName)
                .toArray(String[]::new);

        queryBuilder.append(String.join(",",fieldsName)).append(") VALUES (?");

        for (int i = 1; i < fieldsName.length; i++) {
           queryBuilder.append(",?");
        }
        queryBuilder.append(");");

        return queryBuilder.toString();
    }

    static String toSnakeCase(String key){
        return key.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }

    static int getId(PreparedStatement insertQuery) {
        try (ResultSet rs = insertQuery.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    public Class<T> getEntity() {
        return this.entity;
    }

    public void setEntity(Class<T> entity) {
        this.entity = entity;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
