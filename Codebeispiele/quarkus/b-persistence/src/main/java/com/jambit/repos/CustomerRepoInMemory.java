package com.jambit.repos;

import com.jambit.entities.Customer;
import com.jambit.interceptors.MeasurePerformance;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
@MeasurePerformance
public class CustomerRepoInMemory implements CustomerRepo{
    private AtomicLong nextId = new AtomicLong(1L);
    private Map<Long, Customer> customerMap = new HashMap<>();

    @PostConstruct
    public void postConstruct(){
        log.info("CustomerRepoInMemory was created");
        save(new Customer(null, "Testkunde", LocalDate.of(1900, 1,1), Customer.Status.ORDINARY));
    }

    @Override
    public List<Customer> findAll() {
        return customerMap.values()
                .stream()
                .sorted(Comparator.comparing(Customer::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public void remove(Long id) {
        customerMap.remove(id);
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId()==null){
            customer.setId(nextId.getAndIncrement());
        }
        customerMap.put(customer.getId(), customer);
        return customer;
    }
}
