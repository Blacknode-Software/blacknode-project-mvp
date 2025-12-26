package software.blacknode.backend.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addServersItem(new Server().url("https://test.int.blacknode.software/api").description("Testing (HTTPS)"))
            .addServersItem(new Server().url("https://app.blacknode.software/api").description("Production (HTTPS)"));
    }
}