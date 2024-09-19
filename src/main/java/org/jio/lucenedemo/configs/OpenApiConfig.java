package org.jio.lucenedemo.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Demo lucene",
                version = "1.0.0",
                description = "Ứng dụng Search"
        ),
        servers = {
                @Server(url = "http://localhost:8088", description = "Local Development Server"),
        }
)
@Configuration
public class OpenApiConfig {
}
