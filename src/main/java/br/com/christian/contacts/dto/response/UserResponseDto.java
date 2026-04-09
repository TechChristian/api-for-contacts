package br.com.christian.contacts.dto.response;

import br.com.christian.contacts.database.enums.Roles;

import java.util.UUID;

public record UserResponseDto(
        UUID id,

        String username,

        String email,

        Roles roles
) {}
