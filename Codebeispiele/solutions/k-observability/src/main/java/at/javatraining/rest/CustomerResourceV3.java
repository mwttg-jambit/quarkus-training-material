package at.javatraining.rest;

import at.javatraining.entities.Customer;
import at.javatraining.repos.CustomerPanacheRepository;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/v3/customers")
@Slf4j
public class CustomerResourceV3 {
    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return customerPanacheRepository.listAll(Sort.ascending("name"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Customer findById(@PathParam("id") Long id) {
        Optional<Customer> customerOpt = customerPanacheRepository.findByIdOptional(id);
        if (customerOpt.isEmpty()) throw new NotFoundException("customer with id " + id + " not found");
        return customerOpt.get();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @Transactional
    public Response update(Customer customer, @PathParam("id") Long id) {
        Customer customerInDb = customerPanacheRepository.findById(id);
        if (customerInDb==null) throw new NotFoundException("customer with id " + id + " not found");
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
        customerPanacheRepository.persist(customer);
        return customer;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteCustomer(@PathParam("id") Long id) {
        customerPanacheRepository.deleteById(id);
    }

}
