package at.javatraining.repos;

import at.javatraining.entities.Customer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class) // needs dependency in pom.xml
@TestTransaction // rollback at end of test
class CustomerPanacheRepositoryTest {
    List<Customer> customers = new ArrayList<>();

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    @BeforeEach
    void initCustomers(){
        LocalDate date = LocalDate.now().minusYears(10);
        customers = List.of(
                new Customer("Customer1", date, Customer.Status.KING),
                new Customer( "Customer2", date, Customer.Status.KING),
                new Customer( "Customer3", date, Customer.Status.KING),
                new Customer( "Customer4", date, Customer.Status.VIP),
                new Customer( "Customer5", date, Customer.Status.VIP),
                new Customer( "Customer6", date, Customer.Status.VIP),
                new Customer( "Customer7", date, Customer.Status.ORDINARY),
                new Customer( "Customer8", date, Customer.Status.ORDINARY),
                new Customer( "Customer9", date, Customer.Status.ORDINARY)
        );
    }

    private void saveCustomersToDB() {
        customers.stream()
                .forEach(c -> {
                    customerPanacheRepository.persist(c);
                });
    }

    @Test
    void findAllKings() {
        saveCustomersToDB();
        MatcherAssert.assertThat(customerPanacheRepository.findAllKings().size(), Matchers.equalTo(3));
    }

    @Test
    void findAllVips() {
        saveCustomersToDB();
        MatcherAssert.assertThat(customerPanacheRepository.findAllVips().size(), Matchers.equalTo(3));
    }

    @Test
    void findAllOrdinaries() {
        saveCustomersToDB();
        MatcherAssert.assertThat(customerPanacheRepository.findAllOrdinaries().size(), Matchers.equalTo(3));
    }

    @Test
    void findCustomerByName() {
        saveCustomersToDB();

        long count = customerPanacheRepository.findCustomerByName("Customer1")
                .peek(c -> {
                    MatcherAssert.assertThat(c.getName(), Matchers.containsString("Customer1"));
                })
                .count();
        MatcherAssert.assertThat(count, Matchers.equalTo(1L));

    }
}