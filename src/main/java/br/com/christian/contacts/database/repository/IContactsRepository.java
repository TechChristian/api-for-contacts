package br.com.christian.contacts.database.repository;

import br.com.christian.contacts.database.model.ContactsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContactsRepository extends JpaRepository<ContactsEntity, UUID> {

    Optional<ContactsEntity> findByPhoneAndUsers_Id(String phone, UUID userId);
    Optional<ContactsEntity> findByPhone(String phone);
    List<ContactsEntity> findByUsers_Id(UUID id);
    Optional<ContactsEntity> findByEmailAndUsers_Id(String email, UUID userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM contacts_users WHERE users_id = :userId", nativeQuery = true)
    void deleteUserContacts(@Param("userId") UUID id);
}
