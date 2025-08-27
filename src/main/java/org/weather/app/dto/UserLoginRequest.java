package org.weather.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginRequest {

    @NotBlank(message = "{user.name.notBlank}")
    private String name;

    @NotBlank(message = "{user.name.notBlank}")
    private String password;
}
