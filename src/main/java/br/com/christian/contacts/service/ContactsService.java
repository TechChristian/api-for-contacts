package br.com.christian.contacts.service;

import br.com.christian.contacts.Mappers.ContactsMapper;
import br.com.christian.contacts.database.model.ContactsEntity;
import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.database.repository.IContactsRepository;
import br.com.christian.contacts.database.repository.IUserRepository;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

        if(contactsRepository.findByPhoneAndUsers_Id(create.phone(), create.userID()).isPresent()){
            throw new IllegalArgumentException("Contact with phone number already exists: " + create.phone());
        }

        if(contactsRepository.findByEmailAndUsers_Id(create.email(), create.userID()).isPresent()){
            throw new NonUniqueResultException("Contact with email already exists: " + create.email());
        }

        ContactsEntity contacts = ContactsMapper.toEntity(create);
        Set<UserEntity> users = new HashSet<>();
        users.add(user);
        contacts.setUsers(users);

        return contactsRepository.save(contacts);
    }

    @Transactional
    public ContactsEntity searchByPhoneAndUser(String phone, UUID id){
        return contactsRepository.findByPhoneAndUsers_Id(phone,id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with phone: " + phone));
    }

    @Transactional
    public List<ContactsEntity> searchForContactsById(UUID id){

        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + id)
        );

        List<ContactsEntity> contacts = contactsRepository.findByUsers_Id(id);

       if(contacts.isEmpty()){
            throw new EntityNotFoundException("No contacts found for user with id: " + id);
        }

        return contacts;
    }

    @Transactional
    public List<ContactsEntity> allContacts(){
        return contactsRepository.findAll();
    }
}
