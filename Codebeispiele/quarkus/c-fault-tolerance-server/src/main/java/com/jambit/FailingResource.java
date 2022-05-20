package com.jambit;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customers")
@Slf4j
public class FailingResource {
    List<Customer> customers;
    private AtomicInteger counter = new AtomicInteger(0);
    int c;

    @PostConstruct
    public void postConstruct() {
        customers = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            customers.add(new Customer(i,
                    "Customer" + i,
                    LocalDate.of(1990, 1, 1).plusDays(i),
                    Customer.Status.ORDINARY));
        }
    }

    @SneakyThrows
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers(@QueryParam("block") @DefaultValue("false") boolean block,
                                          @QueryParam("fail") @DefaultValue("false") boolean fail) {
        log.info("getAllCustoemrs called");
        if (fail && counter.incrementAndGet() % 2 == 0){
            log.warn("service is failing");
            throw new RuntimeException("failing");
        }


        if (block && counter.incrementAndGet() % 2 ==0){
            log.warn("service is blocking");
            Thread.sleep(20_000L);
        }

        return customers;
    }
}