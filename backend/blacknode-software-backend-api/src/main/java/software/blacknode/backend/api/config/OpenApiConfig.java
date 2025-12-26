package software.blacknode.backend.api.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addServersItem(new Server().url("https://test.int.blacknode.software/api").description("Testing (HTTPS)"))
            .addServersItem(new Server().url("https://app.blacknode.software/api").description("Production (HTTPS)"));
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