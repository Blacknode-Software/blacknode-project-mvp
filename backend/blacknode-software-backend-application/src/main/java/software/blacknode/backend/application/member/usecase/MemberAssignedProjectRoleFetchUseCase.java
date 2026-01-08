package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAssignedProjectRoleFetchCommand;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAssignedProjectRoleFetchUseCase implements ResultExecutionUseCase<MemberAssignedProjectRoleFetchCommand, MemberAssignedProjectRoleFetchUseCase.Result> {

	private final ProjectAccessControl projectAccessControl;
	
	private final MemberAssociationService memberAssociationService;
	
	private final MemberService memberService;
	
	private final ProjectService projectService;
	
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(MemberAssignedProjectRoleFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getProjectId();
		
		projectAccessControl.ensureMemberHasProjectAccess(memberId, projectId, organizationId, AccessLevel.READ);
		
		var assigneeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assigneeId);
		
		var association = memberAssociationService.getMemberOrganizationAssociationOrThrow(organizationId, assigneeId);
		
		var roleId = association.getRoleId();
		var role = roleService.getOrThrow(organizationId, roleId);
		
		// Not Organization Super Privileged Role - return project specific role
		if(!role.getMeta().isSuperPrivileged()) {		
			association = memberAssociationService.getMemberProjectAssociationOrThrow(organizationId, assigneeId, projectId);
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
