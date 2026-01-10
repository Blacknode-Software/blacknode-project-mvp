package software.blacknode.backend.api.controller.invite.mapper.impl;

import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.response.InvitesListResponse;
import software.blacknode.backend.application.invite.usecase.InvitesInOrganizationUseCase;

public interface InvitesListMapper extends InviteMapper {
	
	@Mapping(target = "ids", source = "result.invitesIds")
	InvitesListResponse toResponse(InvitesInOrganizationUseCase.Result result);
	
}
