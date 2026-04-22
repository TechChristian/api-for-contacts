package br.com.christian.contacts.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDto (
        @Email(message = "Invalid email format")
        String email,
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        String username
){}
