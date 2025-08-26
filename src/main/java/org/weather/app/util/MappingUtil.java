package org.weather.app.util;

import org.modelmapper.ModelMapper;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.model.User;

public class MappingUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();

        MODEL_MAPPER.typeMap(UserRegistrationRequest.class, User.class)
                .addMapping(UserRegistrationRequest::getLogin, User::setName);
    }

    public static User convertToEntity(UserRegistrationRequest dto) {
        return MODEL_MAPPER.map(dto, User.class);
    }

}
