package org.weather.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.weather.app.dto.UserRegistrationRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationRequest> {

    @Override
    public boolean isValid(UserRegistrationRequest dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}