package com.jambit;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/counter")
public class CustomerCounterResource {
    private Client client = ClientBuilder.newClient();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public int countCustomers() {
        Customer[] customers = client.target("http://localhost:8080/customers")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    @GET
    @Path("/retry")
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 3, delay = 500)
    public int countCustomersRetry() {
        Customer[] customers = client.target("http://localhost:8080/customers?fail=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    @GET
    @Path("/failover")
    @Produces(MediaType.APPLICATION_JSON)
    @Fallback(fallbackMethod = "fallback")
    public int countCustomersFailover() {
        Customer[] customers = client.target("http://localhost:8080/customers?fail=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    public int fallback(){
        return 99;
    }

    @GET
    @Path("/timeout")
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout()
    public int countCustomersTimout() {
        Customer[] customers = client.target("http://localhost:8080/customers?block=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    @GET
    @Path("/ciruitbreaker")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 0.25, requestVolumeThreshold = 10)
    public int countCustomersCircuitBreaker() {
        Customer[] customers = client.target("http://localhost:8080/customers?fail=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }


}