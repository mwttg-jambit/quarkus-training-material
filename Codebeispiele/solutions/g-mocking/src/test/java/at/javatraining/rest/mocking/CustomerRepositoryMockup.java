package at.javatraining.rest.mocking;

import at.javatraining.entities.Customer;
import at.javatraining.repos.CustomerRepository;
import io.quarkus.test.Mock;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mock
@Slf4j
public class CustomerRepositoryMockup extends CustomerRepository {
    Customer theOnlyCustomer = new Customer(999L, "The Lonely Customer", LocalDate.of(2000,1,1), Customer.Status.ORDINARY);

    @Override
    public List<Customer> findAll() {
        log.info("MOCK findAll was called");
        return List.of(theOnlyCustomer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        log.info("MOCK findById was called");

        return Optional.of(new Customer(id, "Dummy", LocalDate.now(), Customer.Status.KING));
    }

    @Override
    public Customer save(Customer customer) {
        log.info("MOCK save was called");
        return customer;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        log.info("MOCK deleteCustomer was called");
        return true;
    }
}
