package at.javatraining.events;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

@ApplicationScoped
@Slf4j
public class EventLogger {
    public void customerCreated(@ObservesAsync CustomerCreatedEvent event){
        log.info("Customer created: {}", event);
    }
}
