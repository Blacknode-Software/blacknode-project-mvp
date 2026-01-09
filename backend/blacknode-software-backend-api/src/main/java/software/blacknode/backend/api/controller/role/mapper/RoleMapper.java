package software.blacknode.backend.api.controller.role.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.role.response.content.RoleResponseContent;
import software.blacknode.backend.api.controller.role.response.content.annotation.RoleResponseContentMapping;
import software.blacknode.backend.domain.role.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	@RoleResponseContentMapping
	RoleResponseContent map(Role role);
	
}
