package at.javatraining;

import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@Slf4j
public class Sender {
    private AtomicLong counter = new AtomicLong(1);

    @Inject
    @Channel("customer-out")
    Emitter<Customer> customerEmitter;

    @Scheduled(every = "10s")
    public void send(){
        Customer customer = new Customer(counter.getAndIncrement(), "Customer"+ counter.get(), LocalDate.now().minusMonths(counter.get()), Customer.Status.ORDINARY);
        log.info("Sending: {}", customer);
        customerEmitter.send(customer);
    }
}
