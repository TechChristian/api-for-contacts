package br.com.christian.contacts.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record ContactsResponseDto(
        @Schema(description = "Contact ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Contact's full name", example = "Christian")
        String fullname,

        @Schema(description = "Contact's email", example = "christian@example.com")
        String email,

        @Schema(description = "Contact's phone number", example = "11987654321")
        String phone,

        List<UserResponseDto> users
) {
}
