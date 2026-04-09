package br.com.christian.contacts.database.repository;

import br.com.christian.contacts.database.model.ContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IContactsRepository extends JpaRepository<ContactsEntity, UUID> {

}
