package software.blacknode.backend.application.invite.usecase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InviteDeleteCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class InviteDeleteUseCase implements ExecutionUseCase<InviteDeleteCommand> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final InviteService inviteService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public void execute(InviteDeleteCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
	
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.MANAGE);
		
		var inviteId = command.getInviteId();

		inviteService.delete(organizationId, inviteId);
	}

}
