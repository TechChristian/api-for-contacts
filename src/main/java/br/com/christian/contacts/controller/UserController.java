package br.com.christian.contacts.controller;

import br.com.christian.contacts.Handler.ErrorMessage;
import br.com.christian.contacts.Mappers.UserMapper;
import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.dto.response.MessageResponseDto;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.UserUpdateDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import br.com.christian.contacts.openapi.UsersOpenAPI;
import br.com.christian.contacts.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController implements UsersOpenAPI {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto create) {
        UserEntity user = userService.save(create);
        UserResponseDto response =
                UserMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> searchUserById(@PathVariable UUID id) {
        UserEntity user = userService.listUserById(id);
        UserResponseDto response =
                UserMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto dto) {
        userService.updateFieldsUser(id, dto);
        return ResponseEntity.ok(
                new MessageResponseDto("User updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAllUsers() {
        List<UserEntity> users = userService.listUsers();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseList(users));
    }
}
