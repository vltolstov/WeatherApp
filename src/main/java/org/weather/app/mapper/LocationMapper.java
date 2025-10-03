package org.weather.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.weather.app.dto.LocationRequest;
import org.weather.app.model.Location;
import org.weather.app.model.User;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "locationRequest.location")
    @Mapping(target = "longitude", source = "locationRequest.longitude")
    @Mapping(target = "latitude", source = "locationRequest.latitude")
    @Mapping(target = "user", source = "user")
    Location toEntity(LocationRequest locationRequest, User user);

}
