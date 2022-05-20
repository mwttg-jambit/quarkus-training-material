package at.javatraining;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Path("count")
@ApplicationScoped
@Slf4j
public class CustomerCountResource {
    private Client client = ClientBuilder.newBuilder().newClient();


    @GET
    public int count() {
        Customer[] customers = client.target("http://localhost:8080/v1/customers")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    /*
     method will retry and finally succeed
     */
    @GET
    @Path("retry")
    @Retry(maxRetries = 3, delay = 500)
    public int count2() {
        Customer[] customers = client.target("http://localhost:8080/v1/customers?fail=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    @GET
    @Path("fallback")
    @Fallback(fallbackMethod = "fallback")
    public int count3() {
        Customer[] customers = client.target("http://localhost:8080/v1/customers?fail=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    public int fallback() {
        return 66;
    }


    @GET
    @Path("timeout")
    @Timeout(value = 250, unit = ChronoUnit.MILLIS)
    /* timeout does not work properly, timeout exception thrown after 20 seconds */
    public int count4() {
        log.info("calling now");
        Customer[] customers = client.target("http://localhost:8080/v1/customers?block=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }

    @GET
    @Path("circuitbreaker")
    @CircuitBreaker(failureRatio = 0.25, requestVolumeThreshold = 2)
    /* timeout does not work properly, timeout exception thrown after 20 seconds */
    public int count5() {
        log.info("calling now");
        Customer[] customers = client.target("http://localhost:8080/v1/customers?fail=true")
                .request(MediaType.APPLICATION_JSON)
                .get(Customer[].class);
        return customers.length;
    }


}
