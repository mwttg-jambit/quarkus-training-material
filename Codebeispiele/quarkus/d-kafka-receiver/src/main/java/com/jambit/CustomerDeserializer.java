package com.jambit;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CustomerDeserializer extends ObjectMapperDeserializer<Customer> {
    public CustomerDeserializer() {
        super(Customer.class);
    }
}
