package software.blacknode.backend.api.controller.task.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "task.id"),
	
	@Mapping(target = "title", source = "task.meta.title"),
	@Mapping(target = "description", source = "task.meta.description"),
	@Mapping(target = "priority", source = "task.meta.priority"),
	
	@Mapping(target = "beginAt", source = "task.meta.beginAt"),
	@Mapping(target = "endAt", source = "task.meta.endAt"),
	
	@Mapping(target = "statusId", source = "task.statusId"),
})
public @interface TaskResponseContentMapping {

}
