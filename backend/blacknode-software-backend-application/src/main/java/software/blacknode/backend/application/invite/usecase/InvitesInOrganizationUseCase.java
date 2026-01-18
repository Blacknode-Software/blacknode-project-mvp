package software.blacknode.backend.application.invite.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InvitesInOrganizationCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class InvitesInOrganizationUseCase implements ResultExecutionUseCase<InvitesInOrganizationCommand, InvitesInOrganizationUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final InviteService inviteService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(InvitesInOrganizationCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.MANAGE);
		
		var invites = inviteService.getAll(organizationId);
		
		var invitesIds = invites.stream()
				.map(invite -> invite.getId())
				.toList();
		
		return Result.builder()
				.invitesIds(invitesIds)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<HUID> invitesIds;
		
	}

}
