package software.blacknode.backend.api.controller.organization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Parameter(in = ParameterIn.HEADER, 
		name = "X-Organization-Id", 
		description = "Identifier of the organization", 
		required = true)
public @interface OrganizationHeader {
	
}
