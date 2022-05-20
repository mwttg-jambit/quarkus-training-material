package at.javatraining;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.List;
import java.util.function.Consumer;

@Path("/api/customers")
public class CustomerResource {
    @Inject
    CustomerReactiveRepo customerReactiveRepo;

    @GET
    @Path("/v1")
    public Multi<Customer> findAll1() {
        return customerReactiveRepo.findAll();
    }

    @GET
    @Path("/v2")
    public Multi<Customer> findAll2() {
        return Customer.findAll(Sort.by("name")).stream();
    }


    @GET
    @Path("/{id}")
    public Uni<Customer> findByID(@PathParam("id") Long id) {
        return Customer.findById(id);
    }

    @PUT
    public Uni<Customer> save(Customer customer) {
        return Panache.withTransaction(() -> customer.persist());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@PathParam("id") Long id) {
        return Panache.<Void>withTransaction(() -> Customer.findById(id).chain(c -> c == null ? Uni.createFrom().voidItem() : c.delete()));

        //also working
        //return Panache.withTransaction(() -> Customer.delete("id", id)).replaceWithVoid();
    }


}