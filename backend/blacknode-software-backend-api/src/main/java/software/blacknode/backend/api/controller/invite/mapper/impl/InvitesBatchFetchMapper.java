package software.blacknode.backend.api.controller.invite.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.request.InvitesBatchFetchRequest;
import software.blacknode.backend.api.controller.invite.response.InvitesBatchFetchResponse;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.invite.command.InvitesBatchFetchCommand;
import software.blacknode.backend.application.invite.usecase.InvitesBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface InvitesBatchFetchMapper extends InviteMapper, RequestMapper<InvitesBatchFetchRequest, InvitesBatchFetchCommand>, ResponseMapper<InvitesBatchFetchUseCase.Result, InvitesBatchFetchResponse> {

	@Override
	@Mapping(target = "inviteIds", source = "ids")
	InvitesBatchFetchCommand toCommand(InvitesBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "result.invites")
	InvitesBatchFetchResponse toResponse(InvitesBatchFetchUseCase.Result result);

}
