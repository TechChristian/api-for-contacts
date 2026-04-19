package br.com.christian.contacts.dto.response;

import java.util.List;
import java.util.UUID;

public record ContactsResponseDto(
        UUID id,

        String fullname,

        String email,

        String phone,

        List<UserResponseDto> users
) {
}
