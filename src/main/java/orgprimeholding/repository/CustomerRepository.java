package orgprimeholding.repository;

import orgprimeholding.entities.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerRepository extends BaseRepository implements Repository<CustomerEntity> {
    private static final Logger LOGGER = Logger.getLogger(CustomerRepository.class.getName());
    private static final String LOG_MSG = " customer is stored in DB.";

    public CustomerRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public Integer insert(CustomerEntity entity) {
        Integer id = null;
        if (!exist(entity.getUuid())) {
            try (PreparedStatement query = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
                query.setString(1, entity.getName());
                query.setString(2, entity.getAddress());
                query.setString(3, entity.getUuid());
                query.executeUpdate();

                id = getId(query);
                LOGGER.log(Level.INFO, entity.getName() + LOG_MSG);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            id = returnId(entity.getUuid());
        }
        return id;
    }

    @Override
    public CustomerEntity get(int id) {
        String getQuery = "SELECT * FROM customer WHERE customer_id = " + id;
        CustomerEntity customerEntity = new CustomerEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                customerEntity.setId(resultSet.getInt("customer_id"));
                customerEntity.setName(resultSet.getString("name"));
                customerEntity.setAddress(resultSet.getString("address"));
                customerEntity.setUuid(resultSet.getString("uuid"));
                break;
            }
            return customerEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private boolean exist(String uuid) {
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT * FROM customer" + " WHERE `uuid` = " + uuid);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    private Integer returnId(String uuid) {
        Integer customerId = null;
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT customer_id FROM customer" + " WHERE `uuid` = " + uuid);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                customerId = resultSet.getInt("customer_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return customerId;
    }
}
