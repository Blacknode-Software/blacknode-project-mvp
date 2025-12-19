package software.blacknode.backend.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(basePackages = {"software.blacknode.backend"})
@OpenAPIDefinition(info = @Info(
		title = "Blacknode Software API", 
		version = "0.0.1-DEV", 
		description = "API for Blacknode Software",
		contact = @Contact(
	            name = "Blacknode Software",
	            email = "support@blacknode.software",
	            url = "https://blacknode.software"
	    )))
@EnableJpaRepositories(basePackages = "software.blacknode.backend.infrastructure")
@EntityScan(basePackages = "software.blacknode.backend.infrastructure")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}