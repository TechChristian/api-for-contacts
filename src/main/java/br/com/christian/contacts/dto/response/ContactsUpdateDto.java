package br.com.christian.contacts.dto.response;

import jakarta.validation.constraints.Email;

public record ContactsUpdateDto(
        String fullname,
        @Email(message = "Invalid email format")
        String email
) {}
