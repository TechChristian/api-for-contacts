package br.com.christian.contacts.service;

import br.com.christian.contacts.Mappers.UserMapper;
import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.database.repository.IUserRepository;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {
    private final IUserRepository userRepository;

    @Transactional
    public UserEntity save(UserRequestDto create) {
        userRepository.findByEmail(create.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException(
                            String.format("Email %s already exists", create.email())
                    );
                });
        UserEntity user = UserMapper.toEntity(create);
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity listUserById(UUID id){
      return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %s not found", id))
        );
    }

    @Transactional
    public void deleteUser(UUID id){
     UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %s not found", id))
        );
     userRepository.delete(user);
    }



    @Transactional
    public List<UserEntity> listUsers() {
        return userRepository.findAll();
    }

}

