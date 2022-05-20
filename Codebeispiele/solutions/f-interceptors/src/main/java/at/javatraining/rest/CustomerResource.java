package at.javatraining.rest;

import at.javatraining.entities.Customer;
import at.javatraining.events.CustomerCreatedEvent;
import at.javatraining.interceptors.CountRequests;
import at.javatraining.repos.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Body;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Path("/v1/customers")
@Slf4j
@CountRequests
public class CustomerResource {
    @Inject
    // making field private issues a quarkus warning during
    // build because when private reflection has to be used -> slow
    CustomerRepository customerRepository;

    @Inject
    Validator validator;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Event<CustomerCreatedEvent> customerCreatedEventEvent;

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
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

        if (!constraintViolations.isEmpty()){
            log.debug("There are errors: " + constraintViolations);
            ArrayNode arrayNode = objectMapper.createArrayNode();
            ObjectNode objectNode = objectMapper.createObjectNode();
            arrayNode.add(objectNode);

            constraintViolations.stream()
                    .forEach(cv -> {
                        String fieldName = cv.getPropertyPath().toString();
                        objectNode.put(fieldName,cv.getMessage());
                    });
            return Response.status(Response.Status.BAD_REQUEST).entity(arrayNode.toString()).build();
        }

        customer.setId(id);
        return Response.ok().entity(customerRepository.save(customer)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer insert(@Valid Customer customer) {
        if (customer.getId() != null) {
            // message will be returned in response body
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity("id must be null").build());
        }
        customer = customerRepository.save(customer);
        customerCreatedEventEvent.fireAsync(new CustomerCreatedEvent(Instant.now(), customer.getId()));
        return customer;
    }

    @DELETE
    @Path("{id}")
    public void deleteCustomer(@PathParam("id") Long id) {
        customerRepository.deleteCustomer(id);
    }
}