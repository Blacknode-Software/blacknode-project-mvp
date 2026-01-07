package software.blacknode.backend.api.controller.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.member.response.MemberListResponse;
import software.blacknode.backend.application.member.usecase.MembersInChannelUseCase;
import software.blacknode.backend.application.member.usecase.MembersInOrganizationUseCase;
import software.blacknode.backend.application.member.usecase.MembersInProjectUseCase;

@Mapper(componentModel = "spring")
public interface MemberListMapper extends ControllerMapper {

	@Mapping(target = "ids", source = "memberIds")
	MemberListResponse toOrganizationResponse(MembersInOrganizationUseCase.Result result);
	
	@Mapping(target = "ids", source = "memberIds")
	MemberListResponse toProjectResponse(MembersInProjectUseCase.Result result);
	
	@Mapping(target = "ids", source = "memberIds")
	MemberListResponse toChannelResponse(MembersInChannelUseCase.Result result);

}
