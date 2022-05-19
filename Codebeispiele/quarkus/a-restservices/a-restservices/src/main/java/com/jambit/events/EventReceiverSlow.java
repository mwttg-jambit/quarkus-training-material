package com.jambit.events;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

@ApplicationScoped
@Slf4j
public class EventReceiverSlow {
    @SneakyThrows
    public void customerChangedListener(@ObservesAsync CustomerCreatedEvent event){
        log.info("Received: " + event);
        Thread.sleep(10_000L);
        log.info("Processing finished");
    }
}
