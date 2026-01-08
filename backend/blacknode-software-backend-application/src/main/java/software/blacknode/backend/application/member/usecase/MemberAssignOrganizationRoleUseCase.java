package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAssignOrganizationRoleCommand;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberOrganizationAssociationCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAssignOrganizationRoleUseCase implements ExecutionUseCase<MemberAssignOrganizationRoleCommand> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final MemberAssociationService memberAssociationService;	
	private final MemberService memberService;
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(MemberAssignOrganizationRoleCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(memberId, organizationId, AccessLevel.MANAGE);
		
		var assingeeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assingeeId);
		
		var roleId = command.getRoleId();
		var role = roleService.getOrThrow(organizationId, roleId);
		
		var currentAssoc = memberAssociationService.getMemberOrganizationAssociationOrThrow(organizationId, assingeeId);
		
		memberAssociationService.delete(organizationId, currentAssoc.getId());
		
		var meta = MemberOrganizationAssociationCreationMeta.builder()
				.memberId(assingeeId)
				.organizationId(organizationId)
				.roleId(roleId)
				.build();
		
		var assoc = memberAssociationService.create(organizationId, meta);
	}
}
