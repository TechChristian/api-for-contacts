package br.com.christian.contacts.Mappers;

import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.UserResponseDto;

import java.util.List;

public class UserMapper {
    public static UserEntity toEntity(UserRequestDto request) {
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(request.password());

        return user;
    }

    public static UserResponseDto toResponse(UserEntity user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static List<UserResponseDto> listAll(List<UserEntity> users) {
        return users
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
