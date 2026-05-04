package br.com.christian.contacts;

import br.com.christian.contacts.Handler.ErrorMessage;
import br.com.christian.contacts.dto.request.ContactsRequestDto;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.ContactsResponseDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import br.com.christian.contacts.service.UserService;
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
    @Autowired
    private UserService userService;

    // * cria usuario para testes
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
    public void createContacts_WithDuplicateEmail_Return409(){
        UserResponseDto user = createUser();

     testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "cris lopes", "duplicated@gmail.com" ,"11999999999"))
                .exchange()
                .expectStatus().isCreated();

        ErrorMessage error = testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "jon lopes", "duplicated@gmail.com" ,"11888888888"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getStatus()).isEqualTo(409);
        Assertions.assertThat(error.getPath()).isEqualTo("/v1/contacts");
    }

    @Test
    public void createContacts_WithDuplicatePhone_Return409(){
        UserResponseDto user = createUser();

        testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "cris lopes", "chris@gmail.com" ,"11999999999"))
                .exchange()
                .expectStatus().isCreated();

        ErrorMessage error = testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "jon lopes", "jon@gmail.com" , "11999999999"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getStatus()).isEqualTo(409);
        Assertions.assertThat(error.getPath()).isEqualTo("/v1/contacts");
    }

    @Test
    public void createContacts_WithFieldsInvalid_Return422(){
        UserResponseDto user = createUser();

      ErrorMessage error =  testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "", "chris@gmail.com", "982828"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getStatus()).isEqualTo(422);
        Assertions.assertThat(error.getPath()).isEqualTo("/v1/contacts");
    }

    @Test
    public void getContacts_ByPhoneAndUserId_Return200() {
        UserResponseDto user = createUser();

        ContactsResponseDto created = testClient
                .post()
                .uri("v1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ContactsRequestDto(user.id(), "cris lopes", "cristian@example.com", "11999999999"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ContactsResponseDto.class)
                .returnResult()
                .getResponseBody();

     ContactsResponseDto response =  testClient
                .get()
                .uri("v1/contacts/{phone}/{id}", created.phone(), user.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ContactsResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.id()).isEqualTo(created.id());
        Assertions.assertThat(response.fullname()).isEqualTo("cris lopes");
        Assertions.assertThat(response.email()).isEqualTo("cristian@example.com");
        Assertions.assertThat(response.phone()).isEqualTo("11999999999");
    }

    @Test
    public void deleteContacts_ReturnCode404(){
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

        testClient
                .delete()
                .uri("v1/contacts/{id}", responseBody.id())
                .exchange()
                .expectStatus().isNoContent();

        ErrorMessage error = testClient
                .get()
                .uri("v1/contacts/{id}", responseBody.id())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getStatus()).isEqualTo(404);
        Assertions.assertThat(error.getPath()).isEqualTo("/v1/contacts/" + responseBody.id());

    }


}
