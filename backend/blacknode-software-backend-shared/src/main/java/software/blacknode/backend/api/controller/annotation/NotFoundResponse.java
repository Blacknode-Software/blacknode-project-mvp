package software.blacknode.backend.api.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import software.blacknode.backend.api.controller.response.ErrorResponse;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiResponse(responseCode = "404", 
			description = "Resource not found", 
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
public @interface NotFoundResponse {
}

