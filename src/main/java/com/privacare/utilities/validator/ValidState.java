package com.privacare.utilities.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidState {
    String message() default "Invalid state, available states are: TODO, DONE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
