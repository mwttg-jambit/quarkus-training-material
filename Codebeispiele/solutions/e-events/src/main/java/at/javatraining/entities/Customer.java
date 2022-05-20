package at.javatraining.entities;


import at.javatraining.validation.HabsburgCheck;
import lombok.*;

import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@HabsburgCheck
public class Customer {
    public static enum Status {ORDINARY, VIP, KING};
    private Long id;
    @NonNull
    @Size(min = 5, max = 20)
    private String name;
    @NonNull
    @Past
    private LocalDate dateOfBirth;
    @NonNull
    @NotNull
    private Status status;
}
