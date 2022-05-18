package com.jambit;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    void remove(Long id);
    default void remove(Customer customer){
        remove(customer.getId());
    }

    Customer save(Customer customer);
}
