package br.com.christian.contacts;

import br.com.christian.contacts.Handler.ErrorMessage;
import br.com.christian.contacts.dto.request.UserRequestDto;
import br.com.christian.contacts.dto.response.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsersTest {
    @Autowired
    WebTestClient testClient;

    @Test
    // Test -> Create user with valid information
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
    public void createUser_WithInvalidEntityInformation_ReturnCode422(){
        ErrorMessage errorMessage = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("jon@example", "jon", "123456789"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);
        Assertions.assertThat(errorMessage.getPath()).isEqualTo("/v1/users");

    }

    @Test
    public void createUser_WithInformationPasswordInvalid(){
        ErrorMessage errorMessage = testClient
                .post()
                .uri("v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserRequestDto("chris@example.com", "chris", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

    }

}