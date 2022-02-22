package com.groupproject.nstu.cookbook.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class swaggerConf {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Equipment register")
                                .version("1.5.9")
                                .contact(
                                        new Contact()
                                                .email("stanislav.skii2000@mail.ru")
                                                .name("Stanislav Grokhotov")
                                )
                );
    }

}
