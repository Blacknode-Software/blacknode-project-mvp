package software.blacknode.backend.application.invite.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InviteRevokeCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.invite.meta.modify.impl.InviteRevokeModificationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class InviteRevokeUseCase implements ExecutionUseCase<InviteRevokeCommand> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final InviteService inviteService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(InviteRevokeCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.MANAGE);
		
		var inviteId = command.getInviteId();
		var invite = inviteService.getOrThrow(organizationId, inviteId);
		
		if(invite.isRevoked()) {
			throw new BlacknodeException("Invite already revoked: " + inviteId);
		}
		
		var meta = InviteRevokeModificationMeta.builder()
				.revoked(true)
				.build();
		
		inviteService.modify(organizationId, inviteId, meta);	
	}

}
