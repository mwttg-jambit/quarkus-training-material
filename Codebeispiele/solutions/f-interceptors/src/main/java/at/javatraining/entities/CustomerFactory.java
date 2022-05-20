package at.javatraining.entities;


import at.javatraining.entities.Customer;
import com.github.javafaker.Faker;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class CustomerFactory {

    @Dependent
    @Named
    public List<Customer> testCustomerList() {
        return LongStream.range(1L, 101L)
                .mapToObj(id -> {
                    String name = Faker.instance().funnyName().name();
                    LocalDate dateOfBirth = LocalDate.ofInstant(Faker.instance().date().birthday().toInstant(), ZoneId.systemDefault());
                    Customer.Status status = Customer.Status.values()[ThreadLocalRandom.current().nextInt(0, Customer.Status.values().length)];
                    return new Customer(id, name, dateOfBirth, status);
                })
                .collect(Collectors.toList());
    }

    @Dependent
    @Named
    public Map<Long, Customer> testCustomerMap(@Named("testCustomerList") List<Customer> customerList){
        return customerList.stream()
                .collect(Collectors.toMap(Customer::getId, Function.identity()));
    }

}
