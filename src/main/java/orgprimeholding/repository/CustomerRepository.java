package orgprimeholding.repository;

import orgprimeholding.entities.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerRepository extends BaseRepository  implements Repository<CustomerEntity>{
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

    @Override
    public CustomerEntity get(int id) {
        String getQuery = "SELECT * FROM customer WHERE customer_id = " + id;
        CustomerEntity customerEntity = new CustomerEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                customerEntity.setId(resultSet.getInt("customer_id"));
                customerEntity.setName(resultSet.getString("name"));
                customerEntity.setAddress(resultSet.getString("address"));
                customerEntity.setUuid(resultSet.getString("uuid"));
                resultSet.close();
                break;
            }
            return customerEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
    }


}
