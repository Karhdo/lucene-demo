package org.jio.lucenedemo.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "YounetMedia",
                version = "1.0.0",
                description = "API 1.0"
        ),
        servers = {

        }
)
@Configuration
public class OpenApiConfig {
}
