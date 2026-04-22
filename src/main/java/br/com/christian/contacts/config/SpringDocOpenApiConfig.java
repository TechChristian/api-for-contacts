package br.com.christian.contacts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Rest API - Spring Contacts")
                                .description("API for contacts management")
                                .version("1.0.0")
                                .contact(new Contact().name("Christian Lopes").email("christianx111.ls@gmail.com"))
                        );
    }
}
