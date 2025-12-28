package software.blacknode.backend.application.shared;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.domain.channel.meta.delete.impl.ChannelCascadeDeletionMeta;
import software.blacknode.backend.domain.project.meta.delete.impl.ProjectCascadeDeletionMeta;
import software.blacknode.backend.domain.task.meta.delete.impl.TaskCascadeDeletionMeta;
import software.blacknode.backend.domain.view.meta.delete.impl.ViewCascadeDeletionMeta;

@Service
@RequiredArgsConstructor
public class SharedDeletionService {

	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final ViewService viewService;
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
		
		taskService.delete(organizationId, viewId, meta);
	}
}
