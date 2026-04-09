package br.com.christian.contacts.dto.response;

import br.com.christian.contacts.database.enums.Roles;

public record UserResponseDto(

        String username,

        String email,

        Roles roles
) {}
