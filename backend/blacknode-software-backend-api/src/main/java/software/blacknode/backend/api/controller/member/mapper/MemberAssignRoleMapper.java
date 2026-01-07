package software.blacknode.backend.api.controller.member.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.member.request.MemberAssignRoleRequest;
import software.blacknode.backend.application.member.command.MemberAssignChannelRoleCommand;
import software.blacknode.backend.application.member.command.MemberAssignOrganizationRoleCommand;
import software.blacknode.backend.application.member.command.MemberAssignProjectRoleCommand;

@Mapper(componentModel = "spring")
public interface MemberAssignRoleMapper {

	@Mapping(target = "memberId", source = "memberId")
	MemberAssignOrganizationRoleCommand toOrganizationAssignCommand(MemberAssignRoleRequest request, UUID memberId);

	@Mapping(target = "memberId", source = "memberId")
	@Mapping(target = "projectId", source = "projectId")
	MemberAssignProjectRoleCommand toProjectAssignCommand(MemberAssignRoleRequest request, UUID memberId, UUID projectId);
	
	@Mapping(target = "memberId", source = "memberId")
	@Mapping(target = "channelId", source = "channelId")
	MemberAssignChannelRoleCommand toChannelAssignCommand(MemberAssignRoleRequest request, UUID memberId, UUID channelId);
}
