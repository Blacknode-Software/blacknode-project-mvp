package software.blacknode.backend.api.controller.project.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", source = "project.id", qualifiedByName = "huid2UUID")
@Mapping(target = "name", source = "project.name")
@Mapping(target = "description", source = "project.description")
@Mapping(target = "color", source = "project.color")
public @interface ProjectResponseContentMapping {

}
