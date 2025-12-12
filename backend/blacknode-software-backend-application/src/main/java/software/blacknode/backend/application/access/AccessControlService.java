package software.blacknode.backend.application.access;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.exception.AccessDeniedException;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.role.Role;

@Service
@RequiredArgsConstructor
public class AccessControlService {

	private final MemberAssociationService memberAssociationService;
	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final MemberService memberService;
	private final RoleService roleService;
	private final TaskService taskService;
	
	public void ensureMemberHasOrganizationAccess(HUID memberId, HUID organizationId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var organization = organizationService.getOrThrow(organizationId);
		
		ensureMemberHasOrganizationAccess(member, organization, level);
	}
	
	public void ensureMemberHasOrganizationAccess(Member member, HUID organizationId, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		
		ensureMemberHasOrganizationAccess(member, organization, level);
	}
	
	public void ensureMemberHasOrganizationAccess(HUID memberId, Organization organization, AccessLevel level) {
		var member = memberService.getOrThrow(organization.getId(), memberId);
		
		ensureMemberHasOrganizationAccess(member, organization, level);
	}
	
	public void ensureMemberHasOrganizationAccess(Member member, Organization organization, AccessLevel level) {
		var hasAccess = hasAccessToOrganization(member, organization, level);
		
		var organizationId = organization.getId();
		var memberId = member.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to organization with ID " + organizationId + ".");
		}
	}
	
	public void ensureMemberHasProjectAccess(HUID memberId, HUID projectId, HUID organizationId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var project = projectService.getOrThrow(organizationId, projectId);
		
		ensureMemberHasProjectAccess(member, project, level);
	}
	
	public void ensureMemberHasProjectAccess(Member member, HUID projectId, AccessLevel level) {
		var project = projectService.getOrThrow(member.getOrganizationId(), projectId);
		
		ensureMemberHasProjectAccess(member, project, level);
	}
	
	public void ensureMemberHasProjectAccess(HUID memberId, Project project, AccessLevel level) {
		var member = memberService.getOrThrow(project.getOrganizationId(), memberId);
		
		ensureMemberHasProjectAccess(member, project, level);
	}
	
	public void ensureMemberHasProjectAccess(Member member, Project project, AccessLevel level) {
		var hasAccess = hasAccessToProject(member, project, level);
		
		var memberId = member.getId();
		var projectId = project.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to project with ID " + projectId + ".");
		}
	}
	
	public void ensureMemberHasChannelAccess(HUID memberId, HUID channelId, HUID organizationId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(Member member, HUID channelId, AccessLevel level) {
		var channel = channelService.getOrThrow(member.getOrganizationId(), channelId);
		
		ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(HUID memberId, Channel channel, AccessLevel level) {
		var member = memberService.getOrThrow(channel.getOrganizationId(), memberId);
		
		ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(Member member, Channel channel, AccessLevel level) {
		var hasAccess = hasAccessToChannel(member, channel, level);
		
		var memberId = member.getId();
		var channelId = channel.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to channel with ID " + channelId + ".");		
		}
	}
	
	public AccessLevel getRoleAccessInOrganization(HUID memberId, HUID organizationId) {
		var organization = organizationService.getOrThrow(organizationId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInOrganization(member, organization);
	}
	
	public AccessLevel getRoleAccessInOrganization(Member member, HUID organizationId) {
		var organization = organizationService.getOrThrow(organizationId);
		
		return getRoleAccessInOrganization(member, organization);
	}
	
	public AccessLevel getRoleAccessInOrganization(HUID memberId, Organization organization) {
		var member = memberService.getOrThrow(organization.getId(), memberId);
		
		return getRoleAccessInOrganization(member, organization);
	}
	
	public AccessLevel getRoleAccessInOrganization(Member member, Organization organization) {
		var organizationId = organization.getId();
		
		member.ensureBelongsToOrganization(organizationId);
		
		var association = memberAssociationService.getMemberOrganizationAssociationOrThrow(member.getId(), organization.getId());
		
		var roleId = association.getRoleId();
		
		return getRoleAccess(organizationId, roleId);
	}
	
	public AccessLevel getRoleAccessInProject(HUID memberId, HUID projectId, HUID organizationId) {
		var project = projectService.getOrThrow(organizationId, projectId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInProject(member, project);
	}
	
	public AccessLevel getRoleAccessInProject(Member member, HUID projectId) {
		var project = projectService.getOrThrow(member.getOrganizationId(), projectId);
		
		return getRoleAccessInProject(member, project);
	}
	
	public AccessLevel getRoleAccessInProject(HUID memberId, Project project) {
		var member = memberService.getOrThrow(project.getOrganizationId(), memberId);
		
		return getRoleAccessInProject(member, project);
	}
	
	public AccessLevel getRoleAccessInProject(Member member, Project project) {
		var organizationId = member.getOrganizationId();
		
		project.ensureBelongsToOrganization(organizationId);
		
		var access = getRoleAccessInOrganization(member, organizationId);
		
		if(access.atLeast(AccessLevel.MANAGE)) {
			return access;
		}
		
		var association = memberAssociationService.getMemberProjectAssociationOrThrow(organizationId, member.getId(), project.getId());
		
		var roleId = association.getRoleId();
		
		return getRoleAccess(organizationId, roleId);
	}
	
	public AccessLevel getRoleAccessInChannel(HUID memberId, HUID channelId, HUID organizationId) {
		var channel = channelService.getOrThrow(organizationId, channelId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInChannel(member, channel);
	}
	
	public AccessLevel getRoleAccessInChannel(Member member, HUID channelId) {
		var channel = channelService.getOrThrow(member.getOrganizationId(), channelId);
		
		return getRoleAccessInChannel(member, channel);
	}
	
	public AccessLevel getRoleAccessInChannel(HUID memberId, Channel channel) {
		var member = memberService.getOrThrow(channel.getOrganizationId(), memberId);
		
		return getRoleAccessInChannel(member, channel);
	}
	
	public AccessLevel getRoleAccessInChannel(Member member, Channel channel) {
		var organizationId = member.getOrganizationId();
		
		channel.ensureBelongsToOrganization(organizationId);
		
		var access = getRoleAccessInProject(member, channel.getProjectId());
		
		if(access.atLeast(AccessLevel.MANAGE)) {
			return access;
		}
		
		var association = memberAssociationService.getMemberChannelAssociationOrThrow(organizationId, member.getId(), channel.getId());
		
		var roleId = association.getRoleId();
		
		return getRoleAccess(organizationId, roleId);
	}
	
	private AccessLevel getRoleAccess(HUID organizationId, HUID roleId) {
		var role = roleService.getOrThrow(organizationId, roleId);
		
		return getRoleAccess(role);
	}
	
	private AccessLevel getRoleAccess(Role role) {
		var meta = role.getMeta();
		var scope = role.getScope();
		
		if(scope.isOrganization()) {
			return meta.isSuperPrivileged() ? AccessLevel.MANAGE : AccessLevel.READ;
		}
		
		if(scope.isProject()) {
			return meta.isSuperPrivileged() ? AccessLevel.MANAGE : AccessLevel.READ;
		}
		
		if(scope.isChannel()) {
			return meta.isSuperPrivileged() ? AccessLevel.MANAGE : AccessLevel.WRITE;
		}
		
		throw new BlacknodeException("Unsupported role scope for access level determination: " + scope);
	}
	
	public boolean hasAccessToOrganization(HUID memberId, HUID organizationId, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToOrganization(member, organization, level);
	}
	
	public boolean hasAccessToOrganization(Member member, HUID organizationId, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		
		return hasAccessToOrganization(member, organization, level);
	}
	
	public boolean hasAccessToOrganization(HUID memberId, Organization organization, AccessLevel level) {
		var member = memberService.getOrThrow(organization.getId(), memberId);
		
		return hasAccessToOrganization(member, organization, level);
	}
	
	public boolean hasAccessToOrganization(Member member, Organization organization, AccessLevel level) {
		var access = getRoleAccessInOrganization(member, organization);
		
		return access.atLeast(level);
	}
	
	public boolean hasAccessToProject(HUID memberId, HUID projectId, HUID organizationId, AccessLevel level) {
		var project = projectService.getOrThrow(organizationId, projectId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToProject(member, project, level);
	}
	
	public boolean hasAccessToProject(Member member, HUID projectId, AccessLevel level) {
		var project = projectService.getOrThrow(member.getOrganizationId(), projectId);
		
		return hasAccessToProject(member, project, level);
	}
	
	public boolean hasAccessToProject(HUID memberId, Project project, AccessLevel level) {
		var member = memberService.getOrThrow(project.getOrganizationId(), memberId);
		
		return hasAccessToProject(member, project, level);
	}
	
	public boolean hasAccessToProject(Member member, Project project, AccessLevel level) {
		var access = getRoleAccessInProject(member, project);
		
		return access.atLeast(level);
	}
	
	public boolean hasAccessToChannel(HUID memberId, HUID channelId, HUID organizationId, AccessLevel level) {
		var channel = channelService.getOrThrow(organizationId, channelId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToChannel(member, channel, level);
	}
	
	public boolean hasAccessToChannel(Member member, HUID channelId, AccessLevel level) {
		var channel = channelService.getOrThrow(member.getOrganizationId(), channelId);
		
		return hasAccessToChannel(member, channel, level);
	}
	
	public boolean hasAccessToChannel(HUID memberId, Channel channel, AccessLevel level) {
		var member = memberService.getOrThrow(channel.getOrganizationId(), memberId);
		
		return hasAccessToChannel(member, channel, level);
	}

	public boolean hasAccessToChannel(Member member, Channel channel, AccessLevel level) {
		var access = getRoleAccessInChannel(member, channel);
		
		return access.atLeast(level);
	}
	
	public boolean hasRoleAccess(HUID organizationId, HUID roleId, AccessLevel level) {
		var access = getRoleAccess(organizationId, roleId);
		
		return access.atLeast(level);
	}
	
	public boolean hasRoleAccess(Role role, AccessLevel level) {
		var access = getRoleAccess(role);
		
		return access.atLeast(level);
	}
	
	// TODO access to task, resource
	
	public static enum AccessLevel {
		NONE,
		READ,
		WRITE,
		MANAGE,
		
		;
		
		public boolean atLeast(AccessLevel other) {
			return this.ordinal() >= other.ordinal();
		}

		public boolean hasReadAccess() {
			return this.atLeast(READ);
		}
		
		public boolean hasWriteAccess() {
			return this.atLeast(WRITE);
		}
		
		public boolean hasManageAccess() {
			return this.atLeast(MANAGE);
		}
	}
}
