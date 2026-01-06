package software.blacknode.backend.api.controller.account.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "account.id"),
	@Mapping(target = "firstName", source = "account.meta.firstName"),
	@Mapping(target = "lastName", source = "account.meta.lastName"),
})
public @interface AccountResponseContentMapping {

}
