package com.jambit;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Slf4j
public class CustomerReceiver {
    @Incoming("customers-in")
    public void receiver(Customer customer){
        log.info("Received: " + customer);
    }
}
