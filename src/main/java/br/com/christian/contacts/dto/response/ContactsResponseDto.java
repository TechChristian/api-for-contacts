package br.com.christian.contacts.dto.response;

public record ContactsResponseDto(
        String fullname,

        String email,

        long phone
) {
}
