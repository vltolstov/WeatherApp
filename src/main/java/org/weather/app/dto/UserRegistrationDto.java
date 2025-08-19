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
public class UserRegistrationDto {

    @NotBlank(message = "{user.login.notBlank}")
    @Size(min = 3, max = 15, message = "{user.login.size}")
    @Pattern(
            regexp = ValidationRegex.LOGIN,
            message = "{user.login.hasLettersAndDigitsOnly}"
    )
    private String login;

    @NotBlank(message = "{user.password.notBlank}")
    private String password;

    @NotBlank(message = "{user.confirmPassword.notBlank}")
    private String confirmPassword;

}
