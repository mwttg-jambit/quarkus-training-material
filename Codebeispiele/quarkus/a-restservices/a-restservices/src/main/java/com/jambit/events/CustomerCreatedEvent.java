package com.jambit.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CustomerCreatedEvent {
    private Instant timestamp;
    private Long customerId;
}
