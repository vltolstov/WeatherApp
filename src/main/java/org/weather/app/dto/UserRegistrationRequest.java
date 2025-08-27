package org.weather.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.weather.app.constant.ValidationRegex;
import org.weather.app.validation.PasswordMatches;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PasswordMatches
public class UserRegistrationRequest {

    @NotBlank(message = "{user.name.notBlank}")
    @Size(min = 3, max = 15, message = "{user.name.size}")
    @Pattern(
            regexp = ValidationRegex.NAME,
            message = "{user.name.hasLettersAndDigitsOnly}"
    )
    private String name;

    @NotBlank(message = "{user.password.notBlank}")
    private String password;

    @NotBlank(message = "{user.confirmPassword.notBlank}")
    private String confirmPassword;

}
