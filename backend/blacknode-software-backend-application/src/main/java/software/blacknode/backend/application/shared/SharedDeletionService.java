package software.blacknode.backend.application.shared;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.domain.channel.meta.delete.impl.ChannelCascadeDeletionMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.create.impl.MemberFallbackAssociationCreationMeta;
import software.blacknode.backend.domain.project.meta.delete.impl.ProjectCascadeDeletionMeta;
import software.blacknode.backend.domain.role.meta.delete.impl.RoleCascadeDeletionMeta;
import software.blacknode.backend.domain.task.meta.delete.impl.TaskCascadeDeletionMeta;
import software.blacknode.backend.domain.view.meta.delete.impl.ViewCascadeDeletionMeta;

@Transactional
@Service
@RequiredArgsConstructor
public class SharedDeletionService {

	private final MemberAssociationService memberAssociationService;
	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
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
		var channels = channelService.getAllInProject(organizationId, projectId);
		
		for(var channel : channels) {
			deleteChannelCascade(organizationId, channel.getId());
		}
		
		var meta = ProjectCascadeDeletionMeta.builder().build();
		
		projectService.delete(organizationId, projectId, meta);
	}
	
	public void deleteChannelCascade(HUID organizationId, HUID channelId) {
		var views = viewService.getAllInChannel(organizationId, channelId);
		
		for(var view : views) {
			deleteViewCascade(organizationId, view.getId());
		}
		
		var tasks = taskService.getAllInChannel(organizationId, channelId);
		
		for(var task : tasks) {
			deleteTaskCascade(organizationId, task.getId());
		}
	
		// TODO RESOURCE DELETION HANDLING

		var meta = ChannelCascadeDeletionMeta.builder().build();
		
		channelService.delete(organizationId, channelId, meta);
	}
	
	public void deleteTaskCascade(HUID organizationId, HUID taskId) {
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
	
	
}
