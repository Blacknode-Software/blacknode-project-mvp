package software.blacknode.backend.api.controller.invite.mapper;

import software.blacknode.backend.api.controller.invite.response.content.InviteResponseContent;
import software.blacknode.backend.api.controller.invite.response.content.annotation.InviteResponseContentMapping;
import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.domain.invite.Invite;

public interface InviteMapper extends ControllerMapper {

	@InviteResponseContentMapping
	InviteResponseContent map(Invite invite);
	
}
