package com.jambit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    public enum Status{ORDINARY, VIP, KING}
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Status status;
}
