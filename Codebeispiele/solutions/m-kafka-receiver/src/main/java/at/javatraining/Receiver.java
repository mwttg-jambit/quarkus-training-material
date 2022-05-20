package at.javatraining;

import io.smallrye.common.annotation.Blocking;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Slf4j
public class Receiver {
    @Incoming("customer-in")
    public void consume(Customer customer){
        log.info("Received: {}", customer);
    }
}
