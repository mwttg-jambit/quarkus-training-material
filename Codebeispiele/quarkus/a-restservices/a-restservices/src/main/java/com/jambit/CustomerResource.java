package com.jambit;

import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/customers")
public class CustomerResource {
    @Inject
    CustomerRepo customerRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> allCustomers(){
        return customerRepo.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Customer findById(@PathParam("id") Long id){
        return customerRepo.findById(id).orElseThrow(()-> new NotFoundException());
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer save(Customer customer){
        return customerRepo.save(customer);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id){
        customerRepo.remove(id);
    }
}
