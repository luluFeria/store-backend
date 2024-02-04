package com.app.tienda.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI().components(new Components())
      .info(
        new Info()
          .title("Demo API Santiago")
          .version("1.0.0")
          .description("Mi demo API")
          .contact(new Contact()
            .email("lulu@gmail.com")
            .url("http://lulu-dev.com")
            .name("Lourdes santiago")
          )
      );
  }
}
