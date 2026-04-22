package br.com.christian.contacts.dto.response;

import jakarta.validation.constraints.Email;

public record ContactsUpdateDto(
        String fullname,
        @Email(message = "invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String email
) {}
