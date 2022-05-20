package com.jambit;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends PanacheEntityBase {
    public enum Status{ORDINARY, VIP, KING}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Status status;
}
