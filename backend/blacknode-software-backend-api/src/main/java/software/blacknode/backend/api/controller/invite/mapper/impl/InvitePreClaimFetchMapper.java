package software.blacknode.backend.api.controller.invite.mapper.impl;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.response.InvitePreClaimFetchResponse;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.invite.usecase.InvitePreClaimFetchUseCase;

public interface InvitePreClaimFetchMapper extends InviteMapper, ResponseMapper<InvitePreClaimFetchUseCase.Result, InvitePreClaimFetchResponse> {

	@Override
	InvitePreClaimFetchResponse toResponse(InvitePreClaimFetchUseCase.Result result);

}
