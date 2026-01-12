package software.blacknode.backend.api.controller.profile.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "memberId", source = "profile.memberId"),
	
	@Mapping(target = "displayName", source = "profile.displayName"),
	@Mapping(target = "email", source = "profile.email"),
})
public @interface ProfileResponseContentMapping {

}
