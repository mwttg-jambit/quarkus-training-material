package at.javatraining.entities;


import at.javatraining.validation.HabsburgCheck;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@HabsburgCheck
@Entity
@NamedQueries(
        {
                @NamedQuery(name = "Customer.findByName", query = "select c from Customer c where c.name like ?1")
        }
)
public class Customer extends PanacheEntityBase {
    public static enum Status {ORDINARY, VIP, KING}

    ;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NonNull
    @Size(min = 5, max = 20)
    private String name;
    @NonNull
    @Past
    private LocalDate dateOfBirth;
    @NonNull
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
