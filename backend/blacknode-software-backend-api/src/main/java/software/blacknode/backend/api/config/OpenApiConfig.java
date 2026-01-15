package software.blacknode.backend.api.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;

@Configuration
public class OpenApiConfig {
	
    @Bean
    public OpenAPI customOpenAPI(@Value("${application.base-url:http://localhost:8080}") String applicationUrl) {
        var apiUrl = applicationUrl.endsWith("/") ? applicationUrl + "api" : applicationUrl + "/api";
    	
    	return new OpenAPI()
        	.addServersItem(new Server().url(apiUrl).description("API"))
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
            		.addSecuritySchemes("BearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
    
    @Bean
    public <E extends PatchOperationEnum> OperationCustomizer dynamicPatchDescriptionCustomizer() {
        return (operation, handlerMethod) -> {
            if (handlerMethod != null) {
            	DisplayPatchOperations annotation = handlerMethod.getMethodAnnotation(DisplayPatchOperations.class);
                if (annotation != null && operation.getDescription() != null) {
                	try {
                		var operationClass = (Class<? extends Enum<? extends PatchOperationEnum>>) annotation.value();
                		var operations = PatchOperationEnum.getOperationsList(operationClass);

                        var description = operation.getDescription();
                        
                        description += "\n\n **Allowed patch operations:** " + String.join(", ", operations);
                        
                        operation.setDescription(description);
                	} catch (Exception e) {}
                }
            }
            return operation;
        };
    }
}