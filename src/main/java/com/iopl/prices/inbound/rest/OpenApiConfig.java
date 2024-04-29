package com.iopl.prices.inbound.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pricing API")
                        .description("Pricing API for check the price on a given date.")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Carlos del Hoyo")
                                .email("cdelhoyo@izertis.com")));
    }
}
