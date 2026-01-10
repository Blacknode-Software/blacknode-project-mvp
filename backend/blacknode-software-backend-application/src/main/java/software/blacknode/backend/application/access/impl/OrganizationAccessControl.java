package software.blacknode.backend.application.access.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.exception.AccessDeniedException;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.role.Role;

@Transactional
@Service
@RequiredArgsConstructor
public class OrganizationAccessControl {

	private final MemberAssociationService memberAssociationService;
	private final OrganizationService organizationService;
	private final MemberService memberService;
	private final RoleService roleService;
	
	public void ensureMemberHasOrganizationAccess(HUID organizationId, HUID memberId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var organization = organizationService.getOrThrow(organizationId);
		
		ensureMemberHasOrganizationAccess(organization, member, level);
	}
	
	public void ensureMemberHasOrganizationAccess(HUID organizationId, Member member, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		
		ensureMemberHasOrganizationAccess(organization, member, level);
	}
	
	public void ensureMemberHasOrganizationAccess(Organization organization, HUID memberId, AccessLevel level) {
		var member = memberService.getOrThrow(organization.getId(), memberId);
		
		ensureMemberHasOrganizationAccess(organization, member, level);
	}
	
	public void ensureMemberHasOrganizationAccess(Organization organization, Member member, AccessLevel level) {
		var hasAccess = hasAccessToOrganization(organization, member, level);
		
		var organizationId = organization.getId();
		var memberId = member.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to organization with ID " + organizationId + ".");
		}
	}
	
	public AccessLevel getRoleAccessInOrganization(HUID organizationId, HUID memberId) {
		var organization = organizationService.getOrThrow(organizationId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInOrganization(organization, member);
	}
	
	public AccessLevel getRoleAccessInOrganization(HUID organizationId, Member member) {
		var organization = organizationService.getOrThrow(organizationId);
		
		return getRoleAccessInOrganization(organization, member);
	}
	
	public AccessLevel getRoleAccessInOrganization(Organization organization, HUID memberId) {
		var member = memberService.getOrThrow(organization.getId(), memberId);
		
		return getRoleAccessInOrganization(organization, member);
	}
	
	public AccessLevel getRoleAccessInOrganization(Organization organization, Member member) {
		var organizationId = organization.getId();
		
		member.ensureBelongsToOrganization(organizationId);
		
		var association = memberAssociationService.getMemberOrganizationAssociationOrThrow(organization.getId(), member.getId());
		
		var roleId = association.getRoleId();
		
		return getRoleAccess(organizationId, roleId);
	}
	
	public AccessLevel getRoleAccess(HUID organizationId, HUID roleId) {
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
	
	public boolean hasAccessToOrganization(HUID organizationId, HUID memberId, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToOrganization(organization, member, level);
	}
	
	public boolean hasAccessToOrganization(HUID organizationId, Member member, AccessLevel level) {
		var organization = organizationService.getOrThrow(organizationId);
		
		return hasAccessToOrganization(organization, member, level);
	}
	
	public boolean hasAccessToOrganization(Organization organization, HUID memberId, AccessLevel level) {
		var member = memberService.getOrThrow(organization.getId(), memberId);
		
		return hasAccessToOrganization(organization, member, level);
	}
	
	public boolean hasAccessToOrganization(Organization organization, Member member, AccessLevel level) {
		var access = getRoleAccessInOrganization(organization, member);
		
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
}