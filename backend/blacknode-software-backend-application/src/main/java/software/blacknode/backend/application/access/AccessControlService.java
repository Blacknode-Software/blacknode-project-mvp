package software.blacknode.backend.application.access;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.impl.ViewAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.view.View;

@Transactional
@Service
@RequiredArgsConstructor
public class AccessControlService {

	private final OrganizationAccessControl organizationAccessControl;
	private final ProjectAccessControl projectAccessControl;
	private final ChannelAccessControl channelAccessControl;
	private final TaskAccessControl taskAccessControl;
	private final ViewAccessControl viewAccessControl;
	
	public void ensureMemberHasOrganizationAccess(HUID memberId, HUID organizationId, AccessLevel level) {
		organizationAccessControl.ensureMemberHasOrganizationAccess(memberId, organizationId, level);
	}
	
	public void ensureMemberHasOrganizationAccess(Member member, HUID organizationId, AccessLevel level) {
		organizationAccessControl.ensureMemberHasOrganizationAccess(member, organizationId, level);
	}
	
	public void ensureMemberHasOrganizationAccess(HUID memberId, Organization organization, AccessLevel level) {
		organizationAccessControl.ensureMemberHasOrganizationAccess(memberId, organization, level);
	}
	
	public void ensureMemberHasOrganizationAccess(Member member, Organization organization, AccessLevel level) {
		organizationAccessControl.ensureMemberHasOrganizationAccess(member, organization, level);
	}
	
	public void ensureMemberHasProjectAccess(HUID memberId, HUID projectId, HUID organizationId, AccessLevel level) {
		projectAccessControl.ensureMemberHasProjectAccess(memberId, projectId, organizationId, level);
	}
	
	public void ensureMemberHasProjectAccess(Member member, HUID projectId, AccessLevel level) {
		projectAccessControl.ensureMemberHasProjectAccess(member, projectId, level);
	}
	
	public void ensureMemberHasProjectAccess(HUID memberId, Project project, AccessLevel level) {
		projectAccessControl.ensureMemberHasProjectAccess(memberId, project, level);
	}
	
	public void ensureMemberHasProjectAccess(Member member, Project project, AccessLevel level) {
		projectAccessControl.ensureMemberHasProjectAccess(member, project, level);
	}
	
	public void ensureMemberHasChannelAccess(HUID memberId, HUID channelId, HUID organizationId, AccessLevel level) {
		channelAccessControl.ensureMemberHasChannelAccess(memberId, channelId, organizationId, level);
	}
	
	public void ensureMemberHasChannelAccess(Member member, HUID channelId, AccessLevel level) {
		channelAccessControl.ensureMemberHasChannelAccess(member, channelId, level);
	}
	
	public void ensureMemberHasChannelAccess(HUID memberId, Channel channel, AccessLevel level) {
		channelAccessControl.ensureMemberHasChannelAccess(memberId, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(Member member, Channel channel, AccessLevel level) {
		channelAccessControl.ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasTaskAccess(HUID memberId, HUID taskId, HUID organizationId, AccessLevel level) {
		taskAccessControl.ensureMemberHasTaskAccess(memberId, taskId, organizationId, level);
	}
	
	public void ensureMemberHasTaskAccess(Member member, HUID taskId, AccessLevel level) {
		taskAccessControl.ensureMemberHasTaskAccess(member, taskId, level);
	}
	
	public void ensureMemberHasTaskAccess(HUID memberId, Task task, AccessLevel level) {
		taskAccessControl.ensureMemberHasTaskAccess(memberId, task, level);
	}
	
	public void ensureMemberHasTaskAccess(Member member, Task task, AccessLevel level) {
		taskAccessControl.ensureMemberHasTaskAccess(member, task, level);
	}
	
	public void ensureMemberHasViewAccess(HUID memberId, HUID viewId, HUID organizationId, AccessLevel level) {
		viewAccessControl.ensureMemberHasViewAccess(memberId, viewId, organizationId, level);
	}
	
	public void ensureMemberHasViewAccess(Member member, HUID viewId, AccessLevel level) {
		viewAccessControl.ensureMemberHasViewAccess(member, viewId, level);
	}
	
	public void ensureMemberHasViewAccess(HUID memberId, View view, AccessLevel level) {
		viewAccessControl.ensureMemberHasViewAccess(memberId, view, level);
	}
	
	public void ensureMemberHasViewAccess(Member member, View view, AccessLevel level) {
		viewAccessControl.ensureMemberHasViewAccess(member, view, level);
	}
	
	public boolean hasAccessToOrganization(HUID memberId, HUID organizationId, AccessLevel level) {
		return organizationAccessControl.hasAccessToOrganization(memberId, organizationId, level);
	}
	
	public boolean hasAccessToOrganization(Member member, HUID organizationId, AccessLevel level) {
		return organizationAccessControl.hasAccessToOrganization(member, organizationId, level);
	}
	
	public boolean hasAccessToOrganization(HUID memberId, Organization organization, AccessLevel level) {
		return organizationAccessControl.hasAccessToOrganization(memberId, organization, level);
	}
	
	public boolean hasAccessToOrganization(Member member, Organization organization, AccessLevel level) {
		return organizationAccessControl.hasAccessToOrganization(member, organization, level);
	}
	
	public boolean hasAccessToProject(HUID memberId, HUID projectId, HUID organizationId, AccessLevel level) {
		return projectAccessControl.hasAccessToProject(memberId, projectId, organizationId, level);
	}
	
	public boolean hasAccessToProject(Member member, HUID projectId, AccessLevel level) {
		return projectAccessControl.hasAccessToProject(member, projectId, level);
	}
	
	public boolean hasAccessToProject(HUID memberId, Project project, AccessLevel level) {
		return projectAccessControl.hasAccessToProject(memberId, project, level);
	}
	
	public boolean hasAccessToProject(Member member, Project project, AccessLevel level) {
		return projectAccessControl.hasAccessToProject(member, project, level);
	}
	
	public boolean hasAccessToChannel(HUID memberId, HUID channelId, HUID organizationId, AccessLevel level) {
		return channelAccessControl.hasAccessToChannel(memberId, channelId, organizationId, level);
	}
	
	public boolean hasAccessToChannel(Member member, HUID channelId, AccessLevel level) {
		return channelAccessControl.hasAccessToChannel(member, channelId, level);
	}
	
	public boolean hasAccessToChannel(HUID memberId, Channel channel, AccessLevel level) {
		return channelAccessControl.hasAccessToChannel(memberId, channel, level);
	}

	public boolean hasAccessToChannel(Member member, Channel channel, AccessLevel level) {
		return channelAccessControl.hasAccessToChannel(member, channel, level);
	}
	
	public boolean hasAccessToTask(HUID memberId, HUID taskId, HUID organizationId, AccessLevel level) {
		return taskAccessControl.hasAccessToTask(memberId, taskId, organizationId, level);
	}
	
	public boolean hasAccessToTask(Member member, HUID taskId, AccessLevel level) {
		return taskAccessControl.hasAccessToTask(member, taskId, level);
	}
	
	public boolean hasAccessToTask(HUID memberId, Task task, AccessLevel level) {
		return taskAccessControl.hasAccessToTask(memberId, task, level);
	}
	
	public boolean hasAccessToTask(Member member, Task task, AccessLevel level) {
		return taskAccessControl.hasAccessToTask(member, task, level);
	}
	
	public boolean hasAccessToView(HUID memberId, HUID viewId, HUID organizationId, AccessLevel level) {
		return viewAccessControl.hasAccessToView(memberId, viewId, organizationId, level);
	}
	
	public boolean hasAccessToView(Member member, HUID viewId, AccessLevel level) {
		return viewAccessControl.hasAccessToView(member, viewId, level);
	}
	
	public boolean hasAccessToView(HUID memberId, View view, AccessLevel level) {
		return viewAccessControl.hasAccessToView(memberId, view, level);
	}
	
	public boolean hasAccessToView(Member member, View view, AccessLevel level) {
		return viewAccessControl.hasAccessToView(member, view, level);
	}
	
	public boolean hasRoleAccess(HUID organizationId, HUID roleId, AccessLevel level) {
		return organizationAccessControl.hasRoleAccess(organizationId, roleId, level);
	}
	
	public boolean hasRoleAccess(Role role, AccessLevel level) {
		return organizationAccessControl.hasRoleAccess(role, level);
	}
}