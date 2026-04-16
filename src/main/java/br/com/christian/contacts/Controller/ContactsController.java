package br.com.christian.contacts.Controller;

import br.com.christian.contacts.Mappers.ContactsMapper;
import br.com.christian.contacts.database.model.ContactsEntity;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.dto.response.ContactsResponseDto;
import br.com.christian.contacts.service.ContactsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/contacts")
@RequiredArgsConstructor

public class ContactsController {
    private final ContactsService contactsService;

    @PostMapping
    public ResponseEntity<ContactsResponseDto> createContacts(@Valid @RequestBody ContactsRequestDto create) {
        ContactsEntity contacts = contactsService.save(create);

        ContactsResponseDto response =
                ContactsMapper.toResponse(contacts);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{phone}/{id}")
    public ResponseEntity<ContactsResponseDto> searchPhoneByContacts(
            @PathVariable String phone,
            @PathVariable UUID id) {
        ContactsEntity contacts = contactsService.searchByPhoneAndUser(phone, id);
        return ResponseEntity.status(HttpStatus.OK).body(ContactsMapper.toResponse(contacts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ContactsResponseDto>> searchContactsById(@PathVariable  UUID id){
        List<ContactsEntity> contacts = contactsService.searchForContactsById(id);
        return ResponseEntity.ok(ContactsMapper.toResponseList(contacts));
    }


    @GetMapping
    public ResponseEntity<List<ContactsResponseDto>> listAllContacts() {
        List<ContactsEntity> contacts = contactsService.allContacts();
        return ResponseEntity.ok(ContactsMapper.toResponseList(contacts));
    }
}
