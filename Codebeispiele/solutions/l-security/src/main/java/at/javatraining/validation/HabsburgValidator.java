package at.javatraining.validation;

import at.javatraining.entities.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Locale;

@ApplicationScoped
public class HabsburgValidator implements ConstraintValidator<HabsburgCheck, Customer> {
    @Override
    public void initialize(HabsburgCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Customer customer, ConstraintValidatorContext context) {
        if (customer==null) return true;
        boolean isHabsburger = customer.getName()!=null && customer.getName().toLowerCase().contains("habsburg");
        boolean isKing = customer.getStatus()!=null && customer.getStatus().equals(Customer.Status.KING);
        if (isHabsburger && !isKing) return false;
        return true;
    }
}
