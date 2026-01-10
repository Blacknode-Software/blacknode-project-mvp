package software.blacknode.backend.api.controller.invite.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.request.InviteCreateRequest;
import software.blacknode.backend.api.controller.invite.response.InviteCreateResponse;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.invite.command.InviteCreateCommand;
import software.blacknode.backend.application.invite.usecase.InviteCreateUseCase;

@Mapper(componentModel = "spring")
public interface InviteCreateMapper extends InviteMapper, RequestMapper<InviteCreateRequest, InviteCreateCommand>, ResponseMapper<InviteCreateUseCase.Result, InviteCreateResponse> {

	@Override
	InviteCreateCommand toCommand(InviteCreateRequest request);
	
	@Override
	InviteCreateResponse toResponse(InviteCreateUseCase.Result result);
	
}
