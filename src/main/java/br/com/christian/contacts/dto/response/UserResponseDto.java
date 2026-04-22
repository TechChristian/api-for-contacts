package br.com.christian.contacts.dto.response;

import br.com.christian.contacts.database.enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Users")
public record UserResponseDto(
        @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Username", example = "Christian")
        String username,

        @Schema(description = "user's email", example = "christian@example.com")
        String email,

        @Schema(description = "User profile", example = "USER")
        Roles roles
) {}
