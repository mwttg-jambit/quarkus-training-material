package com.jambit;

import com.jambit.events.CustomerCreatedEvent;
import com.jambit.interceptors.MeasurePerformance;
import lombok.AllArgsConstructor;

import javax.enterprise.event.Event;
import javax.enterprise.event.NotificationOptions;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.List;

@Path("/api/customers")
@MeasurePerformance
public class CustomerResource {
    @Inject
    Event<CustomerCreatedEvent> customerCreatedEventEvent;

    @Inject
    CustomerRepo customerRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> allCustomers() {
        return customerRepo.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Customer findById(@PathParam("id") Long id) {
        return customerRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer save(Customer customer) {
        boolean newCustomer = customer.getId() == null;
        Customer savedCustomer = customerRepo.save(customer);
        if (newCustomer) {
            CustomerCreatedEvent event = new CustomerCreatedEvent(Instant.now(), savedCustomer.getId());
            customerCreatedEventEvent.fireAsync(event);
        }
        return savedCustomer;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        customerRepo.remove(id);
    }
}
