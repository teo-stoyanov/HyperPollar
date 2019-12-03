package orgprimeholding.service;

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

    CustomerEntity getFromDb(Integer customerId){
        return this.customerRepository.get(customerId);
    }

    Integer getCustomerId() {
        return this.customerId;
    }
}
