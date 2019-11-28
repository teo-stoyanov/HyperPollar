package orgprimeholding.service.repository.service;

import orgprimeholding.entities.CustomerEntity;
import orgprimeholding.repository.CustomerRepository;

import java.sql.Connection;

public class CustomerService {
    private Connection connection;
    private CustomerRepository customerRepository;
    private Integer customerId;

    CustomerService(Connection connection) {
        this.connection = connection;
        this.customerRepository = new CustomerRepository(CustomerEntity.class, this.connection);
    }

    void insertToDb(CustomerEntity customerEntity) {
        this.customerId = this.customerRepository.insert(customerEntity);
    }

    Integer getCustomerId() {
        return this.customerId;
    }
}
