package com.hamdan.concert.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Reservation Ticket Service")
                .description("Reservation Ticket Service API Documentation")
                .version("1.0.0")
                .contact(new Contact().name("Hamdan Rijali Triyadi")
                        .email("hamdanrijali@gmail.com")
                        .url("https://www.linkedin.com/in/hamdan-rijali-202b5599/")
                );
    }
}
