package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAssignChannelRoleCommand;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberChannelAssociationCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAssignChannelRoleUseCase implements ExecutionUseCase<MemberAssignChannelRoleCommand> {

	private final AccessControlService accessControlService;
	
	private final MemberAssociationService memberAssociationService;	
	private final MemberService memberService;
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(MemberAssignChannelRoleCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		accessControlService.ensureMemberHasChannelAccess(memberId, channelId, organizationId, AccessLevel.MANAGE);
		
		
		var assingeeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assingeeId);
		
		var roleId = command.getRoleId();
		var role = roleService.getOrThrow(organizationId, roleId);
		
		var currentAssoc = memberAssociationService.getMemberChannelAssociation(organizationId, assingeeId, channelId);
		
		if(currentAssoc.isPresent()) {
			memberAssociationService.delete(organizationId, currentAssoc.get().getId());
		}
		
		var meta = MemberChannelAssociationCreationMeta.builder()
				.memberId(assingeeId)
				.channelId(channelId)
				.roleId(roleId)
				.build();
		
		var assoc = memberAssociationService.create(organizationId, meta);
	}
}
