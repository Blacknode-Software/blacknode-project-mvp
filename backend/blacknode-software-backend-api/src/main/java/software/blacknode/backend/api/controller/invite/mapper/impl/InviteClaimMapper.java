package software.blacknode.backend.api.controller.invite.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.invite.mapper.InviteMapper;
import software.blacknode.backend.api.controller.invite.request.InviteClaimRequest;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.application.invite.command.InviteClaimCommand;

@Mapper(componentModel = "spring")
public interface InviteClaimMapper extends InviteMapper, RequestMapper<InviteClaimRequest, InviteClaimCommand> {

	@Override
	InviteClaimCommand toCommand(InviteClaimRequest request);

}
