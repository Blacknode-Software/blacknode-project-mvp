package software.blacknode.backend.application.access.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.exception.AccessDeniedException;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.project.Project;

@Service
@RequiredArgsConstructor
public class ProjectAccessControl {

	private final MemberAssociationService memberAssociationService;
	private final OrganizationAccessControl organizationAccessControl;
	private final ProjectService projectService;
	private final MemberService memberService;
	
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
		
		var access = organizationAccessControl.getRoleAccessInOrganization(member, organizationId);
		
		if(access.atLeast(AccessLevel.MANAGE)) {
			return access;
		}
		
		var association = memberAssociationService.getMemberProjectAssociationOrThrow(organizationId, member.getId(), project.getId());
		
		var roleId = association.getRoleId();
		
		return organizationAccessControl.getRoleAccess(organizationId, roleId);
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
	
	public AccessLevel getRoleAccess(HUID organizationId, HUID roleId) {
		return organizationAccessControl.getRoleAccess(organizationId, roleId);
	}
}