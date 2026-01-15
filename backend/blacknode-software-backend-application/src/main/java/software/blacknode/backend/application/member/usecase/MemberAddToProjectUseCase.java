package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAddToProjectCommand;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberProjectAssociationCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAddToProjectUseCase implements ExecutionUseCase<MemberAddToProjectCommand> {
	
	private final ProjectAccessControl projectAccessControl;
	
	private final MemberAssociationService memberAssociationService;

	private final RoleService roleService;

	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public void execute(MemberAddToProjectCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getProjectId();
		var addingMemberId = command.getMemberId();
		
		projectAccessControl.ensureMemberHasProjectAccess(organizationId, 
				memberId, projectId, AccessLevel.MANAGE);
		
		if(projectAccessControl.hasAccessToProject(organizationId, addingMemberId, projectId, AccessLevel.READ)) {
			throw new BlacknodeException("Member already has access to project: " + projectId);
		}
		
		var defaultRole = roleService.getProjectRoles(organizationId)
				.stream()
				.filter(role -> role.getMeta().isByDefaultAssigned())
				.findFirst()
				.orElseThrow(() -> new BlacknodeException("No default role found for project: " + projectId));
	
		var meta = MemberProjectAssociationCreationMeta.builder()
				.projectId(projectId)
				.memberId(addingMemberId)
				.roleId(defaultRole.getId())
				.build();
		
		var association = memberAssociationService.create(organizationId, meta);
	}

}
