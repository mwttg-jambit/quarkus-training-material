package at.javatraining.rest;

import at.javatraining.entities.Customer;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/v2/customers")
@Slf4j
public class CustomerResourceV2 {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return Customer.listAll(Sort.ascending("name"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Customer findById(@PathParam("id") Long id) {
        Optional<Customer> customerOpt = Customer.findByIdOptional(id);
        if (customerOpt.isEmpty()) throw new NotFoundException("customer with id " + id + " not found");
        return customerOpt.get();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @Transactional
    public Response update(Customer customer, @PathParam("id") Long id) {
        Customer customerInDb = Customer.findById(id);
        customerInDb.setName(customer.getName());
        customerInDb.setDateOfBirth(customer.getDateOfBirth());
        customerInDb.setStatus(customer.getStatus());
        return Response.ok().entity(customerInDb).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Customer insert(@Valid Customer customer) {
        customer.persist();
        return customer;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteCustomer(@PathParam("id") Long id) {
        Customer.findByIdOptional(id).ifPresent(c -> c.delete());
    }

}
