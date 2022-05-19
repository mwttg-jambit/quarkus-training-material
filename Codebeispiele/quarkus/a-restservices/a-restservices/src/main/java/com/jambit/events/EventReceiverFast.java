package com.jambit.events;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;

@ApplicationScoped
@Slf4j
public class EventReceiverFast {
    @SneakyThrows
    public void customerCreatedListener(@ObservesAsync CustomerCreatedEvent event){
        log.info("Received: " + event);
        Thread.sleep(2000L);
        log.info("Processing finished");
    }

    public void containerStartupListener(@Observes StartupEvent event){
        log.info("Container started");
    }

    public void containerShutdownListener(@Observes ShutdownEvent event){
        log.info("Container shutdown");
    }
}
