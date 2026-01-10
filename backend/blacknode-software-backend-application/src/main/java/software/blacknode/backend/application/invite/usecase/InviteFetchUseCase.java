package software.blacknode.backend.application.invite.usecase;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InviteFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.invite.Invite;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class InviteFetchUseCase implements ResultExecutionUseCase<InviteFetchCommand, InviteFetchUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final InviteService inviteService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(InviteFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.MANAGE);
		
		var inviteId = command.getId();
		var invite = inviteService.getOrThrow(organizationId, inviteId);
		
		return Result.builder()
				.invite(invite)
				.build();
	}

	@Builder
	@Getter
	@ToString
	public static class Result {
		
		@NonNull
		private final Invite invite;
		
	}

}
