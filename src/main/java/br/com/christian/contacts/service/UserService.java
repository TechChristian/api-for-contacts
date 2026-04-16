package br.com.christian.contacts.service;

import br.com.christian.contacts.Mappers.UserMapper;
import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.database.repository.IUserRepository;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.exception.EmailAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

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
    public List<UserEntity> listUsers() {
        return userRepository.findAll();
    }

}

