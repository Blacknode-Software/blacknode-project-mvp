package software.blacknode.backend.application.access;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.project.Project;

@Service
@RequiredArgsConstructor
public class AccessControlService {

	private final MemberAssociationService memberAssociationService;
	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final TaskService taskService;
	
	@Autowired
	private SessionContext sessionContext;
	
	
	public boolean hasAccessToOrganization(HUID organizationId, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		
		return hasAccessToOrganization(organization, level);
	}
	
	public boolean hasAccessToOrganization(Organization organization, AccessLevel level) {
		
		return false;
	}
	
	public boolean hasAccessToProject(HUID projectId, AccessLevel level) {
		var project = projectService.getOrThrow(projectId);
		
		return hasAccessToProject(project, level);
	}
	
	public boolean hasAccessToProject(Project project, AccessLevel level) {
		
		return false;
	}
	
	public boolean hasAcessToProjects(Set<HUID> projectIds, AccessLevel level) {
		var projects = projectService.getByIds(projectIds.stream().toList());
	
		return hasAccessToProjects(projects, level);
	}
	
	public boolean hasAccessToProjects(Collection<Project> projects, AccessLevel level) {
		
		return false;
	}
	
	public boolean hasAccessToChannel(HUID channelId, AccessLevel level) {
		var channel = channelService.getOrThrow(channelId);
		
		return hasAccessToChannel(channel, level);
	}
	
	public boolean hasAccessToChannel(Channel channel, AccessLevel level) {
		
		return false;
	}
	
	public boolean hasAccessToChannels(Set<HUID> channelIds, AccessLevel level) {
		var channels = channelService.getByIds(channelIds.stream().toList());
		
		return hasAccessToChannels(channels, level);
	}
	
	public boolean hasAccessToChannels(Collection<Channel> channels, AccessLevel level) {
	
		return false;
	}
	
	// access to task
	
	public static enum AccessLevel {
		NONE,
		READ,
		WRITE
	}
}
