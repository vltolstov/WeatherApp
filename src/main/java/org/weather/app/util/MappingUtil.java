package org.weather.app.util;

import org.modelmapper.ModelMapper;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.model.User;

public class MappingUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();

//        MODEL_MAPPER.typeMap(User.class, UserRegistrationDto.class).addMappings(mapper -> {
//            mapper.map(User::getId, MatchRequestDto::setId);
//            mapper.map(src -> src.getFirstPlayer().getName(), MatchRequestDto::setFirstPlayerName);
//            mapper.map(src -> src.getSecondPlayer().getName(), MatchRequestDto::setSecondPlayerName);
//            mapper.map(src -> src.getScore().getSetsScore(), MatchRequestDto::setScore);
//            mapper.map(src -> src.getWinner().getName(), MatchRequestDto::setWinnerName);
//        });

        MODEL_MAPPER.typeMap(UserRegistrationRequest.class, User.class)
                .addMapping(UserRegistrationRequest::getLogin, User::setName);
    }

    public static User convertToEntity(UserRegistrationRequest dto) {
        return MODEL_MAPPER.map(dto, User.class);
    }

}
