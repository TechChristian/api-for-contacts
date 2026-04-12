package br.com.christian.contacts.dto.response;

import java.util.List;

public record ContactsResponseDto(
        String fullname,

        String email,

        String phone,

        List<UserResponseDto> users
) {
}
