package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAssignProjectRoleCommand;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberProjectAssociationCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAssignProjectRoleUseCase implements ExecutionUseCase<MemberAssignProjectRoleCommand> {

	private final ProjectAccessControl projectAccessControl;
	
	private final MemberAssociationService memberAssociationService;	
	private final MemberService memberService;
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(MemberAssignProjectRoleCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getProjectId();
		
		projectAccessControl.ensureMemberHasProjectAccess(memberId, projectId, organizationId, AccessLevel.MANAGE);
		
		
		var assingeeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assingeeId);
		
		var roleId = command.getRoleId();
		var role = roleService.getOrThrow(organizationId, roleId);
		
		var currentAssoc = memberAssociationService.getMemberProjectAssociation(organizationId, assingeeId, projectId);
		
		if(currentAssoc.isPresent()) {
			memberAssociationService.delete(organizationId, currentAssoc.get().getId());
		}
		
		var meta = MemberProjectAssociationCreationMeta.builder()
				.memberId(assingeeId)
				.projectId(projectId)
				.roleId(roleId)
				.build();
		
		var assoc = memberAssociationService.create(organizationId, meta);
	}
}
