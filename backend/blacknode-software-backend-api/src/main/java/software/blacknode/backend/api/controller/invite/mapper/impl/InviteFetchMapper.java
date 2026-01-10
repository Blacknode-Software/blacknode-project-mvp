package software.blacknode.backend.api.controller.invite.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.response.InviteResponse;
import software.blacknode.backend.api.controller.invite.response.content.annotation.InviteResponseContentMapping;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.invite.usecase.InviteFetchUseCase;

@Mapper(componentModel = "spring")
public interface InviteFetchMapper extends InviteMapper, ResponseMapper<InviteFetchUseCase.Result, InviteResponse> {

	@Override
	@InviteResponseContentMapping
	InviteResponse toResponse(InviteFetchUseCase.Result result);

}
