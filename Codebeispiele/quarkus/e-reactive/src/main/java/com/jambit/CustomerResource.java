package com.jambit;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import java.util.List;

@Path("/customers")
@Slf4j
public class CustomerResource {
    @GET
    public Multi<Customer> findAllCustomers() {
        log.info("called");
        Multi<Customer> result = Customer.findAll().stream();
        log.info("returned");
        return result;
    }

    @GET
    @Path("/{id}")
    public Uni<Customer> findById(@PathParam("id") Long id) {
        return Customer.findById(id);
    }

    @PUT
    public Uni<Customer> save(Customer customer) {
        return Panache.withTransaction(() -> customer.persist());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@PathParam("id") Long id) {
        /*
        return Panache.withTransaction(() ->
                    Customer.findById(id).chain(c ->
                        c == null ? Uni.createFrom().voidItem() : c.delete()
                    )
                );
                */

        return Panache.withTransaction(() -> Customer.deleteById(id)).replaceWithVoid();
    }

}
