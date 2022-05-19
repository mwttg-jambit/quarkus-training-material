package com.jambit.repos;

import com.jambit.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
class CustomerRepoPanacheTest {
    List<Customer> customers;

    @Inject
    CustomerRepoPanache repo;

    @BeforeEach
    void beforeEach() {
        LocalDate date = LocalDate.now().minusYears(30);
        customers = List.of(
                new Customer(null, "C1", date, Customer.Status.ORDINARY),
                new Customer(null, "C2", date, Customer.Status.VIP),
                new Customer(null, "C3", date, Customer.Status.VIP),
                new Customer(null, "C4", date, Customer.Status.KING),
                new Customer(null, "C5", date, Customer.Status.KING),
                new Customer(null, "C6", date, Customer.Status.KING)
        );
    }

    @Test
    void findAllKings() {
        long countBeforeInsert = repo.findAll().count();
        customers.forEach(c -> repo.persist(c));
        assertEquals(countBeforeInsert + customers.size(), repo.findAll().count());
        assertEquals(3, repo.findAllKings().size());
    }

    @Test
    void findAllVips() {
    }

    @Test
    void findAllOrdinaries() {
    }

    @Test
    void findByNameLike() {
        customers.forEach(c -> repo.persist(c));
        boolean allMatch = repo.findByNameLike("C%").stream().allMatch(c -> c.getName().toUpperCase().contains("C"));

        assertTrue(repo.findByNameLike("C%").size() > 0);
        assertTrue(allMatch);
    }
}