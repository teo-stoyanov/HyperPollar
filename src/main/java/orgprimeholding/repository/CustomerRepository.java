package orgprimeholding.repository;

import orgprimeholding.entities.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerRepository extends BaseRepository  {
    private static final Logger LOGGER = Logger.getLogger(CustomerRepository.class.getName());

    public CustomerRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public int insert(CustomerEntity entity) {
        try (PreparedStatement query = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, entity.getName());
            query.setString(2, entity.getAddress());
            query.setString(3, entity.getUuid());
            query.executeUpdate();

            Integer id = getId(query);
            if (id != null) return id;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return -1;
    }




}
