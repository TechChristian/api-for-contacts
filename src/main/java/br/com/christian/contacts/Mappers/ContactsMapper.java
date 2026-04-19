package br.com.christian.contacts.Mappers;
import br.com.christian.contacts.database.model.ContactsEntity;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.dto.response.ContactsResponseDto;
import br.com.christian.contacts.dto.response.UserResponseDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ContactsMapper {
    public static ContactsEntity toEntity(ContactsRequestDto request) {
        ContactsEntity contacts = new ContactsEntity();

        contacts.setFullname(request.fullname());
        contacts.setEmail(request.email());
        contacts.setPhone(request.phone());

        return contacts;
    }
    public static ContactsResponseDto toResponse(ContactsEntity contacts){
        var usersDto = contacts.getUsers()
                .stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();

        return new ContactsResponseDto(
                contacts.getId(),
                contacts.getFullname(),
                contacts.getEmail(),
                contacts.getPhone(),
                usersDto
        );
    }

    public static List<ContactsResponseDto> toResponseList(List<ContactsEntity> contacts) {
        return contacts
                .stream()
                .map(ContactsMapper::toResponse)
                .toList();
    }
}
