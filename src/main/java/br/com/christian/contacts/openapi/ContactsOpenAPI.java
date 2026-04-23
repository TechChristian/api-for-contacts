package br.com.christian.contacts.openapi;

import br.com.christian.contacts.Handler.ErrorMessage;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.dto.response.ContactsResponseDto;
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

public interface ContactsOpenAPI {
    @Operation(
            summary = "Create a new contact",
            description = "This feature a create new contact",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contact created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactsResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Contact with phone number or email already exists: ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Unprocessed entity", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<ContactsResponseDto> createContacts(@Valid @RequestBody ContactsRequestDto create);

    @Operation(
            summary = "List all contacts",
            description = "This feature list all contacts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contacts listed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactsResponseDto.class)))
            }
    )
    public ResponseEntity<List<ContactsResponseDto>> listAllContacts();

    @Operation(
            summary = "Search contact by phone and user ID",
            description = "This feature search contact by phone and user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contact found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Contact not found with the provided phone and user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<ContactsResponseDto> searchPhoneByUserID(@PathVariable String phone, @PathVariable UUID id);
}
