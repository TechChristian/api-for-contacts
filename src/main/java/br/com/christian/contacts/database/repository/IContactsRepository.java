package br.com.christian.contacts.database.repository;

import br.com.christian.contacts.database.model.ContactsEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContactsRepository extends JpaRepository<ContactsEntity, UUID> {

    Optional<ContactsEntity> findByPhoneAndUsers_Id(String phone, UUID userId);
    Optional<ContactsEntity> findByPhone(String phone);
    List<ContactsEntity> findByUsers_Id(UUID id);
    Optional<ContactsEntity> findByEmailAndUsers_Id(String email, UUID userId);
}
