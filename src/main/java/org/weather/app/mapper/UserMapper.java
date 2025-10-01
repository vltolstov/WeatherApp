package org.weather.app.mapper;

import org.mapstruct.Mapper;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegistrationRequest userRegistrationRequest);

}
