package at.javatraining.rest;

import at.javatraining.entities.Customer;
import at.javatraining.repos.CustomerRepository;
import org.jboss.resteasy.annotations.Body;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/v1/customers")
public class CustomerResource {
    @Inject
    // making field private issues a quarkus warning during
    // build because when private reflection has to be used -> slow
    CustomerRepository customerRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Customer findById(@PathParam("id") Long id) {
        return customerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(Customer customer, @PathParam("id") Long id) {
        if (id == null) {
            throw new BadRequestException("id must not be null"); // message will not be returned in repsonse body
        }
        customer.setId(id);
        return Response.ok().entity(customerRepository.save(customer)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer insert(Customer customer) {
        if (customer.getId() != null) {
            // message will be returned in response body
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity("id must be null").build());
        }
        return customerRepository.save(customer);
    }

    @DELETE
    @Path("{id}")
    public void deleteCustomer(@PathParam("id") Long id) {
        customerRepository.deleteCustomer(id);
    }
}