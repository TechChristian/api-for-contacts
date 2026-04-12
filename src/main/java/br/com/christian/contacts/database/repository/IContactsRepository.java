package br.com.christian.contacts.database.repository;

import br.com.christian.contacts.database.model.ContactsEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IContactsRepository extends JpaRepository<ContactsEntity, UUID> {

    Optional<Object> findByPhone(String phone);
}
