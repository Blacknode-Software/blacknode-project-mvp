package software.blacknode.backend.api.controller.role.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "role.id"),
	
	@Mapping(target = "name", source = "role.meta.name"),
	@Mapping(target = "description", source = "role.meta.description"),
	@Mapping(target = "color", source = "role.meta.color"),
	
	@Mapping(target = "isByDefault", source = "role.meta.byDefaultAssigned"),
	@Mapping(target = "isSystemRole", source = "role.meta.systemDefault"),
	@Mapping(target = "isSuperPrivileged", source = "role.meta.superPrivileged"),
})
public @interface RoleResponseContentMapping {

}
