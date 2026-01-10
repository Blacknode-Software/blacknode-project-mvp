package software.blacknode.backend.api.controller.role.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.role.mapper.RoleMapper;
import software.blacknode.backend.api.controller.role.response.RolesListResponse;
import software.blacknode.backend.application.role.usecase.RolesInOrganizationUseCase;

@Mapper(componentModel = "spring")
public interface RolesListMapper extends RoleMapper, ControllerMapper {

	@Mapping(target = "ids", source = "rolesIds")
	RolesListResponse toOrganizationResponse(RolesInOrganizationUseCase.Result result);
	
}
