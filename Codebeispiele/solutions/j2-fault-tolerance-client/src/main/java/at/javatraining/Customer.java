package at.javatraining;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    public static enum Status {ORDINARY, VIP, KING};
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate dateOfBirth;
    @NonNull
    private Status status;
}
