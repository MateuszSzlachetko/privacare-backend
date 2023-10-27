package com.privacare.utilities.validator;

import com.privacare.model.enums.StateEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StateValidator implements ConstraintValidator<ValidState, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValidState constraintAnnotation) {
        acceptedValues = Arrays.stream(StateEnum.values())
                .map(stateEnum -> stateEnum.toString().toUpperCase())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        return acceptedValues.contains(value.toUpperCase());
    }
}
