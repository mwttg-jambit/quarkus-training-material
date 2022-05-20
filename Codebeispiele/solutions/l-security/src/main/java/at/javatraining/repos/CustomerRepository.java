package at.javatraining.repos;

import at.javatraining.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> findAllCustomers();

    Optional<Customer> findCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    boolean deleteCustomer(Long id);
}
