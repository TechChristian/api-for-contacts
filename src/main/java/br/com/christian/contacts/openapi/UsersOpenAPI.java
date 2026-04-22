package br.com.christian.contacts.openapi;

import br.com.christian.contacts.Handler.ErrorMessage;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.MessageResponseDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import br.com.christian.contacts.dto.response.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface UsersOpenAPI {
    @Operation(
            summary = "Create a new user",
            description = "A feature to create a new user while ensuring email uniqueness.",
            responses = {
                    @ApiResponse(responseCode = "201",description = "User created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409",description = "Email already exist.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Unprocessed entity", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto create);

    @Operation(
            summary = "Search for a user by ID",
            description = "A feature to for a search user by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found by ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<UserResponseDto> searchUserById(@PathVariable UUID id);

    @Operation(
            summary = "Update fields by user",
            description = "A feature to update specific fields for a user using their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User update successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found by ID:  ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Unprocessed entity", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<MessageResponseDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto dto);

    @Operation(
            summary = "Delete a user by ID",
            description = "A feature delete user by ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "User not found by ID: ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id);

    @Operation(
            summary = "List all users",
            description = "This feature a list all users registered",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users returned successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)))
            }
    )
    public ResponseEntity<List<UserResponseDto>> listAllUsers();
}
