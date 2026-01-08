package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAssignedChannelRoleFetchCommand;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAssignedChannelRoleFetchUseCase implements ResultExecutionUseCase<MemberAssignedChannelRoleFetchCommand, MemberAssignedChannelRoleFetchUseCase.Result> {

	private final ChannelAccessControl channelAccessControl;
	
	private final MemberAssociationService memberAssociationService;
	
	private final MemberService memberService;
	
	private final ChannelService channelService;
	
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(MemberAssignedChannelRoleFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		channelAccessControl.ensureMemberHasChannelAccess(memberId, channelId, organizationId, AccessLevel.READ);
		
		var assigneeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assigneeId);
		
		var association = memberAssociationService.getMemberOrganizationAssociationOrThrow(organizationId, assigneeId);
		
		var roleId = association.getRoleId();
		var role = roleService.getOrThrow(organizationId, roleId);
		
		// Not Organization Super Privileged Role
		if(!role.getMeta().isSuperPrivileged()) {		
			// Return project specific role
			var projectId = channel.getProjectId();
			
			association = memberAssociationService.getMemberProjectAssociationOrThrow(organizationId, assigneeId, projectId);
			
			roleId = association.getRoleId();
			role = roleService.getOrThrow(organizationId, roleId);
			
			// Not Project Super Privileged Role
			if(!role.getMeta().isSuperPrivileged()) {
				// Return channel specific role
				association = memberAssociationService.getMemberChannelAssociationOrThrow(organizationId, assigneeId, channelId);
			}
		}
		
		return Result.builder()
				.association(association)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final MemberAssociation association;
		
	}
}
