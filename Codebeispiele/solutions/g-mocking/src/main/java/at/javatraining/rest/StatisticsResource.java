package at.javatraining.rest;

import at.javatraining.events.CustomerCreatedEvent;
import at.javatraining.interceptors.CountRequests;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/v1/statistics")
@Slf4j
@ApplicationScoped
@CountRequests
public class StatisticsResource {
    private AtomicInteger customerCreatedCounter = new AtomicInteger(0);

    @GET
    @Path("customers")
    public AtomicInteger numberOfCustomersCreated(){
        return customerCreatedCounter;
    }

    public void countCustomer(@ObservesAsync CustomerCreatedEvent event){
        customerCreatedCounter.incrementAndGet();
        log.info("Customers created: {}", customerCreatedCounter);
    }
}
