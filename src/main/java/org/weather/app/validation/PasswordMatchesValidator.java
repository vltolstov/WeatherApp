package org.weather.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.weather.app.dto.UserRegistrationDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}