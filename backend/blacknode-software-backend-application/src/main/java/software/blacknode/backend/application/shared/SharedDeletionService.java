package software.blacknode.backend.application.shared;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.domain.channel.meta.delete.impl.ChannelCascadeDeletionMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberFallbackAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.delete.impl.MemberAssociationCascadeDeletionMeta;
import software.blacknode.backend.domain.project.meta.delete.impl.ProjectCascadeDeletionMeta;
import software.blacknode.backend.domain.role.meta.delete.impl.RoleCascadeDeletionMeta;
import software.blacknode.backend.domain.task.assign.meta.delete.impl.TaskAssignCascadeDeletionMeta;
import software.blacknode.backend.domain.task.meta.delete.impl.TaskCascadeDeletionMeta;
import software.blacknode.backend.domain.view.meta.delete.impl.ViewCascadeDeletionMeta;

@Transactional
@Service
@RequiredArgsConstructor
public class SharedDeletionService {

	private final ProjectAccessControl projectAccessControl;
	private final ChannelAccessControl channelAccessControl;
	
	private final MemberAssociationService memberAssociationService;
	private final OrganizationService organizationService;
	private final TaskAssignService taskAssignService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final MemberService memberService;
	private final ViewService viewService;
	private final RoleService roleService;
	private final TaskService taskService;
	
	/* TO BE IMPLEMENTED LATER
	public void deleteOrganizationCascade(HUID organizationId) {
		var projects = projectService.getAll(organizationId);
		
		for(var project : projects) {
			deleteProjectCascade(organizationId, project.getId());
		}
		
		organizationService.delete(organizationId);
	}
	*/
	
	public void deleteProjectCascade(HUID organizationId, HUID projectId) {
		var project = projectService.getOrThrow(organizationId, projectId);
		
		var channels = channelService.getAllInProject(organizationId, projectId);
		
		for(var channel : channels) {
			deleteChannelCascade(organizationId, channel.getId());
		}
		
		var members = memberService.getAll(organizationId)
				.stream()
				.filter(m -> projectAccessControl.hasAccessToProject(m, project, AccessLevel.READ))
				.map(member -> member.getId())
				.toList();
		
		for(var member : members) {
			deleteMemberFromProject(organizationId, member, projectId);
		}
		
		var meta = ProjectCascadeDeletionMeta.builder().build();
		
		projectService.delete(organizationId, projectId, meta);
	}
	
	public void deleteChannelCascade(HUID organizationId, HUID channelId) {
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		var views = viewService.getAllInChannel(organizationId, channelId);
		
		for(var view : views) {
			deleteViewCascade(organizationId, view.getId());
		}
		
		var tasks = taskService.getAllInChannel(organizationId, channelId);
		
		for(var task : tasks) {
			deleteTaskCascade(organizationId, task.getId());
		}
	
		var members = memberService.getAll(organizationId)
				.stream()
				.filter(m -> channelAccessControl.hasAccessToChannel(m, channel, AccessLevel.READ))
				.map(member -> member.getId())
				.toList();
		
		for(var member : members) {
			deleteMemberFromChannel(organizationId, member, channelId);
		}

		var meta = ChannelCascadeDeletionMeta.builder().build();
		
		channelService.delete(organizationId, channelId, meta);
	}
	
	public void deleteTaskCascade(HUID organizationId, HUID taskId) {
		unassignMembersFromTask(organizationId, taskId);
		
		var meta = TaskCascadeDeletionMeta.builder().build();
		
		taskService.delete(organizationId, taskId, meta);
	}
	
	public void deleteViewCascade(HUID organizationId, HUID viewId) {
		var meta = ViewCascadeDeletionMeta.builder().build();
		
		viewService.delete(organizationId, viewId, meta);
	}
	
	public void deleteRoleCascade(HUID organizationId, HUID roleId) {
		var meta = RoleCascadeDeletionMeta.builder().build();
	
		var role = roleService.getOrThrow(organizationId, roleId);
		
		var scope = role.getScope();
		
		var defaultRoleId = roleService.getAll(organizationId)
				.stream()
				.filter(r -> r.getScope() == scope)
				.filter(r -> r.getMeta().isByDefaultAssigned())
				.findFirst()
				.orElseThrow(() -> new BlacknodeException("No default role found for scope " + scope))
				.getId();
		
		var associations = memberAssociationService.getMemberAssociationsByRole(organizationId, roleId);
		
		for(var association : associations) {
			assignFallbackRoleToMember(organizationId, association, defaultRoleId);
		}
		
		roleService.delete(organizationId, roleId, meta);
	}
	
	public void deleteMemberFromProject(HUID organizationId, HUID memberId, HUID projectId) {
		var project = projectService.getOrThrow(organizationId, projectId);
		
		var channels = channelService.getAllInProject(organizationId, projectId);
		
		for(var channel : channels) {
			deleteMemberFromChannel(organizationId, memberId, channel.getId());
		}
		
		removeMemberProjectAssociation(organizationId, memberId, projectId);
	}
	
	public void deleteMemberFromChannel(HUID organizationId, HUID memberId, HUID channelId) {
		/* TODO: Add task assignment removal? */
		
		removeMemberChannelAssociation(organizationId, memberId, channelId);
	}
	
	
	private void unassignMembersFromTask(HUID organizationId, HUID taskId) {
		var assigns = taskAssignService.getByTaskId(organizationId, taskId);
		
		var meta = TaskAssignCascadeDeletionMeta.builder().build();
		
		for(var assign : assigns) {
			taskAssignService.delete(organizationId, assign.getId(), meta);
		}
	}
	
	private void assignFallbackRoleToMember(HUID organizationId, MemberAssociation association, HUID defaultRoleId) {
		var memberId = association.getMemberId();
		var associationId = association.getId();
		var entityId = association.getEntityId();
		
		memberAssociationService.delete(organizationId, associationId);
		
		var fallbackMeta = MemberFallbackAssociationCreationMeta.builder()
				.memberId(memberId)
				.roleId(defaultRoleId)
				.entityId(entityId)
				.build();
		
		memberAssociationService.create(organizationId, fallbackMeta);
	}
	
	private void removeMemberProjectAssociation(HUID organizationId, HUID memberId, HUID projectId) {
		var associationOpt = memberAssociationService.getMemberProjectAssociation(organizationId, memberId, projectId);
		
		if(associationOpt.isEmpty()) return;
		
		var association = associationOpt.get();
		
		var meta = MemberAssociationCascadeDeletionMeta.builder()
				.build();
		
		memberAssociationService.delete(organizationId, association.getId(), meta);
	}
	
	private void removeMemberChannelAssociation(HUID organizationId, HUID memberId, HUID channelId) {
		var associationOpt = memberAssociationService.getMemberChannelAssociation(organizationId, memberId, channelId);
		
		if(associationOpt.isEmpty()) return;
		
		var association = associationOpt.get();
		
		var meta = MemberAssociationCascadeDeletionMeta.builder()
				.build();
		
		memberAssociationService.delete(organizationId, association.getId(), meta);
	}
	
}
