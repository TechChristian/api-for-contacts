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
import org.springframework.web.bind.annotation.RequestBody;

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
}
