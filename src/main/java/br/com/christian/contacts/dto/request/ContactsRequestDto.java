package br.com.christian.contacts.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ContactsRequestDto (
        @NotNull(message = "User ID is required")
        UUID userID,

        @NotBlank(message = "Full name is required")
        String fullname,

        @NotBlank(message = "Email is required")
        @Email(message = "invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String email,

        @NotBlank(message = "Phone number is required")
        @Size(min = 10, max = 12, message = "Phone number must be between 10 and 12 characters long")
        String phone
){}
