package software.blacknode.backend.api.controller.task.status.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "status.id"),
	
	@Mapping(target = "name", source = "status.name"),
	@Mapping(target = "description", source = "status.description"),
	@Mapping(target = "color", source = "status.color"),
})
public @interface TaskStatusResponseContentMapping {

}
