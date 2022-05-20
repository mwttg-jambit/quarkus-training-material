package at.javatraining.repos;

import at.javatraining.entities.Customer;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
@DefaultBean
public class CustomerMemoryRepository implements CustomerRepository {
    @Inject
    @Named("testCustomerMap")
    Map<Long, Customer> customerMap;

    AtomicLong lastId = new AtomicLong();

    @PostConstruct
    void init() {
        long maxId = customerMap.keySet().stream().mapToLong(k -> Long.valueOf(k)).max().orElse(0L);
        lastId.set(maxId);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerMap.values().stream()
                .sorted(Comparator.comparing(Customer::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(lastId.incrementAndGet());
        }
        customerMap.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        return customerMap.remove(id) != null;
    }
}
