package br.com.christian.contacts.Mappers;
import br.com.christian.contacts.database.model.ContactsEntity;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.dto.response.ContactsResponseDto;
import br.com.christian.contacts.dto.response.UserResponseDto;

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
                contacts.getFullname(),
                contacts.getEmail(),
                contacts.getPhone(),
                usersDto
        );
    }
}
