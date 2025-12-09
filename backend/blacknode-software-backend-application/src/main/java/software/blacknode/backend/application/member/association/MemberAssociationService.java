package software.blacknode.backend.application.member.association;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.MemberAssociationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberChannelAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberOrganizationAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberProjectAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.delete.MemberAssociationDeletionMeta;
import software.blacknode.backend.domain.member.association.repository.MemberAssociationRepository;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.meta.RoleMeta;

/*
 * Current behavior:
 * - A member can have only one role per scope (organization, project, channel)
 * - Setting a new role for a member in a scope replaces the previous role
 * - Roles must belong to the same organization as the member
 * - Removing superior roles will return previous roles if they exist (maybe change later)
 * - If a member has a super privileged role in a superior scope, that role is returned for inferior scopes
 */

@Service
@RequiredArgsConstructor
public class MemberAssociationService {
	
	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final MemberService memberService;
	private final RoleService roleService;
	
	private final MemberAssociationRepository repository;
	
	public Role getMemberRoleInOrganizationOrThrow(HUID memberId, HUID organizationId) {
		return getMemberRoleInOrganization(memberId, organizationId, null)
				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role in organization with ID " + organizationId));
	}
	
	private Optional<Role> getMemberRoleInOrganization(HUID memberId, HUID organizationId, Member member) {
		if(member == null) member = memberService.getMemberOrThrow(memberId);
	
		member.ensureBelongsToOrganization(organizationId);
		
		var association = findAssociation(memberId, organizationId, MemberAssociationMeta.Scope.ORGANIZATION);
		
		return association.flatMap(a -> roleService.getById(a.getRoleId()));
	}
	
	public Role getMemberRoleInProjectOrThrow(HUID memberId, HUID projectId) {
		return getMemberRoleInProject(memberId, projectId, null).orElseThrow(() -> 
			new BlacknodeException("Member with ID " + memberId + " has no role in project with ID " + projectId));
	}
	
	private Optional<Role> getMemberRoleInProject(HUID memberId, HUID projectId, Member member) {
		if(member == null) member =  memberService.getMemberOrThrow(memberId);
		
		var project = projectService.getProjectOrThrow(projectId);
		project.ensureBelongsToOrganization(member.getOrganizationId());
		
		/* check if superior role (organization) is a privileged role */
		
		var organizationRole = getMemberRoleInOrganization(memberId, member.getOrganizationId(), member);
		
		if(organizationRole.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false)) {
			return organizationRole;
		}
		
		var association = findAssociation(memberId, projectId, MemberAssociationMeta.Scope.PROJECT);
		
		return association.flatMap(a -> roleService.getById(a.getRoleId()));
	}
	
	public Role getMemberRoleInChannelOrThrow(HUID memberId, HUID channelId) {
		return getMemberRoleInChannel(memberId, channelId)
				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role in channel with ID " + channelId));
	}
	
	public Optional<Role> getMemberRoleInChannel(HUID memberId, HUID channelId) {
		var member = memberService.getMemberOrThrow(memberId);
		
		var channel = channelService.getChannelOrThrow(channelId);
		channel.ensureBelongsToOrganization(member.getOrganizationId());
		
		/* check if superior role (project/organization) is a privileged role */
		var projectRole = getMemberRoleInProject(memberId, channel.getProjectId(), member);
		
		if(projectRole.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false)) {
			return projectRole;
		}
		
		var association = findAssociation(memberId, channelId, MemberAssociationMeta.Scope.CHANNEL);
		
		return association.flatMap(a -> roleService.getById(a.getRoleId()));
	}
	
	public void setOrganizationRoleToMember(HUID memberId, HUID roleId, HUID organizationId) {
		var organization = organizationService.getOrganizationOrThrow(organizationId);
		
		var member = memberService.getMemberOrThrow(memberId);
		member.ensureBelongsToOrganization(organizationId);
		
		var role = roleService.getByIdOrThrow(roleId);
		role.ensureBelongsToOrganization(organizationId);
		role.ensureHasScope(Role.Scope.ORGANIZATION);
		
		removeAssociationIfExists(memberId, organizationId, MemberAssociationMeta.Scope.ORGANIZATION);
		
		var newAssociationMeta = MemberOrganizationAssociationCreationMeta.builder()
				.memberId(memberId)
				.roleId(roleId)
				.organizationId(organizationId)
				.build();
		
		var newAssociation = new MemberAssociation();
		
		newAssociation.create(newAssociationMeta);
		
		repository.save(newAssociation);
	}
	
	public void setProjectRoleToMember(HUID memberId, HUID roleId, HUID projectId) {
		var member = memberService.getMemberOrThrow(memberId);

		var organizationId = member.getOrganizationId();
		
		var role = roleService.getByIdOrThrow(roleId);
		role.ensureBelongsToOrganization(organizationId);
		role.ensureHasScope(Role.Scope.PROJECT);
		
		var project = projectService.getProjectOrThrow(projectId);
		project.ensureBelongsToOrganization(organizationId);
		
		removeAssociationIfExists(memberId, projectId, MemberAssociationMeta.Scope.PROJECT);
		
		var newAssociationMeta = MemberProjectAssociationCreationMeta.builder()
				.memberId(memberId)
				.roleId(roleId)
				.projectId(projectId)
				.build();
		
		var newAssociation = new MemberAssociation();
		
		newAssociation.create(newAssociationMeta);
		
		repository.save(newAssociation);
		
	}
	
	public void setChannelRoleToMember(HUID memberId, HUID roleId, HUID channelId) {
		var member = memberService.getMemberOrThrow(memberId);

		var organizationId = member.getOrganizationId();
		
		var role = roleService.getByIdOrThrow(roleId);
		role.ensureBelongsToOrganization(organizationId);
		role.ensureHasScope(Role.Scope.CHANNEL);
		
		var channel = channelService.getChannelOrThrow(channelId);
		channel.ensureBelongsToOrganization(organizationId);
		
		removeAssociationIfExists(memberId, channelId, MemberAssociationMeta.Scope.CHANNEL);
		
		var newAssociationMeta = MemberChannelAssociationCreationMeta.builder()
				.memberId(memberId)
				.roleId(roleId)
				.channelId(channelId)
				.build();
		
		var newAssociation = new MemberAssociation();
		
		newAssociation.create(newAssociationMeta);
		
		repository.save(newAssociation);
		
	}
	
	public Optional<MemberAssociation> findAssociation(HUID memberId, HUID scopeId, MemberAssociationMeta.Scope scope) {
		var associations = repository.getByMemberId(memberId);
		
		return associations.stream()
			.filter(assoc -> assoc.getMeta().getScope() == scope)
			.filter(assoc -> assoc.getScopeId().equals(scopeId))
			.findFirst();
	}
	
	public MemberAssociation findAssociationOrThrow(HUID memberId, HUID scopeId, MemberAssociationMeta.Scope scope) {
		return findAssociation(memberId, scopeId, scope)
				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role association in scope " + scope));
	}
	
		
	private void removeAssociationIfExists(HUID memberId, HUID scopeId, MemberAssociationMeta.Scope scope) {
		var currentAssociation = findAssociation(memberId, scopeId, scope);
		
		if(currentAssociation.isPresent()) {
			var assoc = currentAssociation.get();
		
			var assocDeletionMeta = MemberAssociationDeletionMeta.builder()
					.build();
			
			assoc.delete(assocDeletionMeta);
			
			repository.save(assoc);
		}
	}
	
	public boolean isMemberInProject(HUID memberId, HUID projectId) {
		return getMemberRoleInProject(memberId, projectId, null).isPresent();
	}
	
	public boolean isMemberInChannel(HUID memberId, HUID channelId) {
		return getMemberRoleInChannel(memberId, channelId).isPresent();
	}
}
