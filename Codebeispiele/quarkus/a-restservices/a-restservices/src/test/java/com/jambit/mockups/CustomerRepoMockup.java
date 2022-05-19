package com.jambit.mockups;

import com.jambit.Customer;
import com.jambit.CustomerRepo;
import io.quarkus.test.Mock;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
//@Mock //uncomment when using CustomerResourceMockupTest
public class CustomerRepoMockup implements CustomerRepo {
    @Override
    public List<Customer> findAll() {
        log.info("Mockup findAll was called");
        return List.of(new Customer(1L,"From Muckup", LocalDate.now(), Customer.Status.VIP));
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }
}
