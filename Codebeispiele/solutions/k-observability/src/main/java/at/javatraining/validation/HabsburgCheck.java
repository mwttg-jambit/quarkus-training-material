package at.javatraining.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = HabsburgValidator.class)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HabsburgCheck {
    String message() default
            "a Habsburger must be KING";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

