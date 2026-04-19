package br.com.christian.contacts.dto.request;

import jakarta.validation.constraints.Email;

public record UserUpdateDto (
        @Email(message = "Invalid email format")
        String email,
        String username
){}
