package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.member.command.MemberAddToChannelCommand;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberChannelAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberProjectAssociationCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberAddToChannelUseCase implements ExecutionUseCase<MemberAddToChannelCommand> {
	
	private final ChannelAccessControl channelAccessControl;
	
	private final ProjectAccessControl projectAccessControl;
	
	private final MemberAssociationService memberAssociationService;

	private final RoleService roleService;
	
	private final ChannelService channelService;

	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public void execute(MemberAddToChannelCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		var addingMemberId = command.getMemberId();
		
		var projectId = channel.getProjectId();
		
		channelAccessControl.ensureMemberHasChannelAccess(organizationId, 
				memberId, channelId, AccessLevel.MANAGE);
		
		if(channelAccessControl.hasAccessToChannel(organizationId, addingMemberId, channelId, AccessLevel.READ)) {
			throw new BlacknodeException("Member already has access to channel: " + channelId);
		}
		
		if(!projectAccessControl.hasAccessToProject(organizationId, addingMemberId, projectId, AccessLevel.READ)) {
			var defaultProjectRole = roleService.getProjectRoles(organizationId)
					.stream()
					.filter(role -> role.getMeta().isByDefaultAssigned())
					.findFirst()
					.orElseThrow(() -> new BlacknodeException("No default role found for project: " + projectId));
			
			var meta = MemberProjectAssociationCreationMeta.builder()
					.projectId(projectId)
					.memberId(addingMemberId)
					.roleId(defaultProjectRole.getId())
					.build();
			
			memberAssociationService.create(organizationId, meta);
		}
		
		var defaultChannelRole = roleService.getChannelRoles(organizationId)
				.stream()
				.filter(role -> role.getMeta().isByDefaultAssigned())
				.findFirst()
				.orElseThrow(() -> new BlacknodeException("No default role found for channel: " + channelId));
		
		var meta = MemberChannelAssociationCreationMeta.builder()
				.channelId(channelId)
				.memberId(addingMemberId)
				.roleId(defaultChannelRole.getId())
				.build();
		
		var association = memberAssociationService.create(organizationId, meta);
	}

}
