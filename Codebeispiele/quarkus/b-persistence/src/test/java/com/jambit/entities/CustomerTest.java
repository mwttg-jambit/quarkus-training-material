package com.jambit.entities;

import com.jambit.repos.CustomerRepoPanache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CustomerTest {
    List<Customer> customers;


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
    @Transactional
    void testPersist(){
        long countBeforeTest = Customer.findAll().count();
        customers.forEach(c -> c.persist());
        List<Customer> list = Customer.findAll().list();
        assertEquals(countBeforeTest + customers.size(), list.size());
    }

}