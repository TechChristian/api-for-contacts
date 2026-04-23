package br.com.christian.contacts.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ContactsUpdateDto(
        @Size(min = 5, max = 50, message = "Full name must be between 5 and 50 characters long")
        String fullname,

        @Email(message = "invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String email
) {}
