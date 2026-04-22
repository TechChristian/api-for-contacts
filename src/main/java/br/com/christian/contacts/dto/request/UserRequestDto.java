package br.com.christian.contacts.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @Email(message = "Invalid email format", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Username is required")
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters long")
        String username,

        @NotNull
        @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters long")
        String password
) { }
