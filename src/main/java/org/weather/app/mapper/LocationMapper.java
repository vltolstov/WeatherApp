package org.weather.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.weather.app.dto.AddLocationRequest;
import org.weather.app.model.Location;
import org.weather.app.model.User;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "user", source = "user")
    Location toEntity(AddLocationRequest addLocationRequest, User user);

}
