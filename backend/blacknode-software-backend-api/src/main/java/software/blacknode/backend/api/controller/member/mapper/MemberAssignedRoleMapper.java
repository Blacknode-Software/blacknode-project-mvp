package software.blacknode.backend.api.controller.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.member.response.MemberAssignedRoleResponse;
import software.blacknode.backend.application.member.usecase.MemberAssignedChannelRoleFetchUseCase;
import software.blacknode.backend.application.member.usecase.MemberAssignedOrganizationRoleFetchUseCase;
import software.blacknode.backend.application.member.usecase.MemberAssignedProjectRoleFetchUseCase;

@Mapper(componentModel = "spring")
public interface MemberAssignedRoleMapper extends ControllerMapper {

	@Mapping(target = "roleId", source="association.roleId")
	MemberAssignedRoleResponse toOrganizationResponse(MemberAssignedOrganizationRoleFetchUseCase.Result result);
	
	@Mapping(target = "roleId", source="association.roleId")
	MemberAssignedRoleResponse toProjectResponse(MemberAssignedProjectRoleFetchUseCase.Result result);
	
	@Mapping(target = "roleId", source="association.roleId")
	MemberAssignedRoleResponse toChannelResponse(MemberAssignedChannelRoleFetchUseCase.Result result);

}
