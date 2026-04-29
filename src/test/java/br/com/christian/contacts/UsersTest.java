package br.com.christian.contacts;

import br.com.christian.contacts.Handler.ErrorMessage;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.MessageResponseDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import br.com.christian.contacts.dto.response.UserUpdateDto;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsersTest {
    @Autowired
    WebTestClient testClient;

    // Test -> POST
    @Test
    public void createUser_WithValidInformation_ReturnCode201(){
        UserResponseDto responseBody = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("chris@example.com", "christian", "123456789"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.id()).isNotNull();
        Assertions.assertThat(responseBody.email()).isEqualTo("chris@example.com");
        Assertions.assertThat(responseBody.username()).isEqualTo("christian");
        Assertions.assertThat(responseBody.roles().getRole().isEmpty());
    }

    @Test
    public void createUser_WithDuplicateEmailInformation_ReturnCode409(){
        ErrorMessage errorMessage = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("jon@example.com", "jones", "123456789"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(409);
        Assertions.assertThat(errorMessage.getPath()).isEqualTo("/v1/users");


    }

    @Test
    public void createUser_WithInvalidData_Return422() {
        ErrorMessage error = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("chris@gmail", "chris", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getStatus()).isEqualTo(422);
    }

    // Test -> GET
    @Test
    public void getAllUser_Return200(){
          testClient
                .get()
                .uri("v1/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    public void getUserById_Return200(){
        UserResponseDto responseBody = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("chris@example.com", "chris", "123455666"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        testClient
                .get()
                .uri("/v1/users/{id}", responseBody.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(responseBody.id())
                .jsonPath("$.email").isEqualTo(responseBody.email())
                .jsonPath("$.username").isEqualTo(responseBody.username());
    }

    @Test
    public void getUser_ById_NotFound_Return404(){
      ErrorMessage error = testClient
                .get()
                .uri("/v1/users/{id}", UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

      Assertions.assertThat(error).isNotNull();
      Assertions.assertThat(error.getStatus()).isEqualTo(404);

    }

    @Test
    public void updateFields_ById_Return200(){
        UserResponseDto responseBody = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("chris@example.com", "chris", "12345467899"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        MessageResponseDto response = testClient
                .patch()
                .uri("/v1/users/{id}", responseBody.id())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdateDto("chrisjones@example.com", "jones"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(MessageResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response.message()).isEqualTo("User updated successfully");

        UserResponseDto responseBodyUpdated = testClient
                .get()
                .uri("/v1/users/{id}", responseBody.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(responseBodyUpdated).isNotNull();
        Assertions.assertThat(responseBodyUpdated.id()).isEqualTo(responseBody.id());
        Assertions.assertThat(responseBodyUpdated.email()).isEqualTo("chrisjones@example.com");
        Assertions.assertThat(responseBodyUpdated.username()).isEqualTo("jones");

    }

    @Test
    public void deleteUser_ById_Return204(){
        UserResponseDto responseBody = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("chris@example.com", "chris", "12345467899"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        testClient
                .delete()
                .uri("/v1/users/{id}", responseBody.id())
                .exchange()
                .expectStatus().isNoContent();

        ErrorMessage error = testClient
                .get()
                .uri("/v1/users/{id}", responseBody.id())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(error).isNotNull();
        Assertions.assertThat(error.getStatus()).isEqualTo(404);

    }
}