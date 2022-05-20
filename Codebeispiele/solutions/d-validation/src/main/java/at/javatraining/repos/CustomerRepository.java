package at.javatraining.repos;

import at.javatraining.entities.Customer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class CustomerRepository {
    @Inject
    @Named("testCustomerMap")
    Map<Long, Customer> customerMap;

    AtomicLong lastId = new AtomicLong();

    @PostConstruct
    void init() {
        long maxId = customerMap.keySet().stream().mapToLong(k -> Long.valueOf(k)).max().orElse(0L);
        lastId.set(maxId);
    }

    public List<Customer> findAll() {
        return customerMap.values().stream()
                .sorted(Comparator.comparing(Customer::getName))
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(lastId.incrementAndGet());
        }
        customerMap.put(customer.getId(), customer);
        return customer;
    }

    public boolean deleteCustomer(Long id) {
        return customerMap.remove(id) != null;
    }
}
