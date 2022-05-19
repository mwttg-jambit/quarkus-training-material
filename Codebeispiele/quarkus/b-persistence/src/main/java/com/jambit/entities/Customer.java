package com.jambit.entities;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "Customer.findByName", query = "select c from Customer c where c.name like ?1 order by c.name")
})
public class Customer extends PanacheEntityBase {
    public enum Status{ORDINARY, VIP, KING}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Status status;
}
