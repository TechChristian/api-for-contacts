package br.com.christian.contacts;

import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.ContactsResponseDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ContactsTest {

    @Autowired
    WebTestClient testClient;

    private UserResponseDto createUser() {
        return
                testClient
                        .post()
                        .uri("v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new UserRequestDto("user" + UUID.randomUUID() + "@example.com", "christian", "12345678900"))
                        .exchange()
                        .expectStatus().isCreated()
                        .expectBody(UserResponseDto.class)
                        .returnResult()
                        .getResponseBody();
    }

    // Test -> POST
    @Test
    public void createContacts_WithValidInformation_Return201() {
        UserResponseDto user = createUser();

        ContactsResponseDto responseBody = testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "cris lopes", "cristian@example.com", "11999999999"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ContactsResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(responseBody.id()).isNotNull();
        Assertions.assertThat(responseBody.fullname()).isEqualTo("cris lopes");
        Assertions.assertThat(responseBody.email()).isEqualTo("cristian@example.com");
        Assertions.assertThat(responseBody.phone()).isEqualTo("11999999999");
    }

    @Test
    public void createContacts_WithInvalidInformation_Return400(){

    }
}
