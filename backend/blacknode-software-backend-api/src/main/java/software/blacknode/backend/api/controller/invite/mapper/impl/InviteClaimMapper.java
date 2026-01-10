package software.blacknode.backend.api.controller.invite.mapper.impl;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.request.InviteClaimRequest;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.application.invite.command.InviteClaimCommand;

public interface InviteClaimMapper extends InviteMapper, RequestMapper<InviteClaimRequest, InviteClaimCommand> {

	@Override
	InviteClaimCommand toCommand(InviteClaimRequest request);

}
