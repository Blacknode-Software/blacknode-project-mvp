package software.blacknode.backend.application.invite.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InviteCreateCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.invite.meta.create.impl.InviteMemberCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class InviteCreateUseCase implements ResultExecutionUseCase<InviteCreateCommand, InviteCreateUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final AccountService accountService;
	
	private final InviteService inviteService;
	
	private final SessionContextHolder sessionContextHolder;
	
	public static final int INVITE_EXPIRATION_DAYS_DEFAULT = 7;
	
	@Override
	@Transactional
	public Result execute(InviteCreateCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.MANAGE);
		
		var email = command.getEmail();
		
		var account = accountService.getByEmail(email);
		
		/* Add account recreation / assign functionality later */
		if(account.isPresent()) {
			throw new BlacknodeException("Account with email " + email + " already exists.");
		}
		
		checkForActiveInvitations(organizationId, email);
		
		var expiresAt = Timestamp.now().addDays(INVITE_EXPIRATION_DAYS_DEFAULT);
		
		var meta = InviteMemberCreationMeta.builder()
				.email(email)
				.expiresAt(expiresAt)
				.build();
		
		var invite = inviteService.create(organizationId, meta);
	
		return Result.builder()
				.inviteId(invite.getId())
				.token(invite.getToken())
				.build();
	}
	
	private void checkForActiveInvitations(HUID organizationId, String email) {
		var invites = inviteService.getAllByEmail(organizationId, email);
		
		for(var invite : invites) {
			if(invite.isExpired() || invite.isClaimed() || invite.isRevoked()) {
				continue;
			}
			
			throw new BlacknodeException("An active invite for email " + email + " already exists.");
		}
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final HUID inviteId;
		
		@NonNull
		private final String token;
		
	}

}
