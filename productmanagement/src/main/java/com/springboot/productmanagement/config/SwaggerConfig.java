package com.springboot.productmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                    .title("Product Management API")
                    .version("1.0.0")
                    .description("This is a RESTful API for managing products.")
                    .contact(new Contact()
                        .name("NMJ")
                        .email("nmj@gmail.com")
                        .url("https://github.com/nmj0001")
                    )
                    .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0")
                    )
                );
    }
}
