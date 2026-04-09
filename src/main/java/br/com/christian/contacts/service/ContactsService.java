package br.com.christian.contacts.service;

import br.com.christian.contacts.Mappers.ContactsMapper;
import br.com.christian.contacts.database.model.ContactsEntity;
import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.database.repository.IContactsRepository;
import br.com.christian.contacts.database.repository.IUserRepository;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class ContactsService {
    private final IContactsRepository contactsRepository;
    private final IUserRepository userRepository;

    @Transactional
    public ContactsEntity save(ContactsRequestDto create){

        UserEntity user = userRepository.findById(create.userID()).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + create.userID())
        );

        ContactsEntity contacts = ContactsMapper.toEntity(create);
        Set<UserEntity> users = new HashSet<>();
        users.add(user);
        contacts.setUsers(users);

        return contactsRepository.save(contacts);
    }
}
