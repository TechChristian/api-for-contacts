package br.com.christian.contacts.dto.response;

import jakarta.validation.constraints.Email;

public record UserUpdateDto (
        @Email(message = "Invalid email format")
        String email,
        String username
){}
