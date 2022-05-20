package at.javatraining.entities;


import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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
