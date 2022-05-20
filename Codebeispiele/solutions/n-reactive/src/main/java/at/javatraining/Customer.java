package at.javatraining;



import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@NamedQueries(
        {
                @NamedQuery(name = "Customer.findByName", query = "select c from Customer c where c.name like ?1")
        }
)
public class Customer extends PanacheEntityBase {
    public static enum Status {ORDINARY, VIP, KING}

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate dateOfBirth;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
