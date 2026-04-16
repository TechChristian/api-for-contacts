package br.com.christian.contacts.Controller;

import br.com.christian.contacts.Mappers.UserMapper;
import br.com.christian.contacts.database.model.UserEntity;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import br.com.christian.contacts.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto create) {
        UserEntity user = userService.save(create);
        UserResponseDto response =
                UserMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> searchUserById(@PathVariable UUID id){
       UserEntity user = userService.listUserById(id);
        UserResponseDto response =
                UserMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAllUsers(){
        List<UserEntity> users = userService.listUsers();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseList(users));
    }

}
