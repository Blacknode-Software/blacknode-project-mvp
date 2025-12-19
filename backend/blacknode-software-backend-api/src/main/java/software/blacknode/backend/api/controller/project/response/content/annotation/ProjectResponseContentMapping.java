package software.blacknode.backend.api.controller.project.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "project.id", qualifiedByName = "huid2UUID"),
	@Mapping(target = "name", source = "project.meta.name"),
	@Mapping(target = "description", source = "project.meta.description"),
	@Mapping(target = "color", source = "project.meta.color"),
})
public @interface ProjectResponseContentMapping {

}
