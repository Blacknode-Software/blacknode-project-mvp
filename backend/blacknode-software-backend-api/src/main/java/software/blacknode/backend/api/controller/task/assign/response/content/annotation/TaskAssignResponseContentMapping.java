package software.blacknode.backend.api.controller.task.assign.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "assign.id"),
	
	@Mapping(target = "memberId", source = "assign.memberId"),
	@Mapping(target = "taskId", source = "assign.taskId"),
})
public @interface TaskAssignResponseContentMapping {

	
}
