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

    @GetMapping
    public ResponseEntity<List<ContactsResponseDto>> listAllContacts(){
        List<ContactsEntity> contacts = contactsService.listContacts();
        return ResponseEntity.status(HttpStatus.OK).body(ContactsMapper.toResponseList(contacts));
    }
}
