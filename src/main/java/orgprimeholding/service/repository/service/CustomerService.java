package orgprimeholding.service.repository.service;

import orgprimeholding.entities.CustomerEntity;
import orgprimeholding.repository.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerService {
    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
    private static final String LOG_MSG = " customer is stored in DB.";

    private Connection connection;
    private CustomerRepository customerRepository;

    public CustomerService(Connection connection) {
        this.connection = connection;
        this.customerRepository = new CustomerRepository(CustomerEntity.class, connection);
    }

    public Integer insertIfNotExist(CustomerEntity customerEntity) {
        Integer customerId;
        if (!isExisted(customerEntity.getUuid())) {
            customerId = this.customerRepository.insert(customerEntity);
            LOGGER.log(Level.INFO, customerEntity.getName() + LOG_MSG);
        } else {
            customerId = returnId(customerEntity.getUuid());
        }

        return customerId;
    }

    private boolean isExisted(String uuid) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM customer" + " WHERE `uuid` = " + uuid);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    private Integer returnId(String uuid) {
        Integer customerId = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT customer_id FROM customer" + " WHERE `uuid` = " + uuid);
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
