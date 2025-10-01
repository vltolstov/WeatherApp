package org.weather.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRegistrationRequest userRegistrationRequest);

}
