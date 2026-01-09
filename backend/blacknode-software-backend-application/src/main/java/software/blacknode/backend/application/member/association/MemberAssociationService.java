package software.blacknode.backend.application.member.association;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.delete.impl.MemberAssociationDefaultDeletionMeta;
import software.blacknode.backend.domain.member.association.repository.MemberAssociationRepository;


@Transactional
@Service
@RequiredArgsConstructor
public class MemberAssociationService {
	
	private final MemberAssociationRepository repository;
	
	public MemberAssociation create(HUID organizationId, CreationMeta meta) {
		var association = new MemberAssociation(organizationId);
		
		association.create(meta);
		
		repository.save(organizationId, association);
		
		return association;
	}
	
	public void delete(HUID organizationId, HUID associationId) {
		var association = getOrThrow(organizationId, associationId);
		
		var deletionMeta = MemberAssociationDefaultDeletionMeta.builder()
				.build();
		
		association.delete(deletionMeta);
		
		repository.save(organizationId, association);
	}
	
	public void delete(HUID organizationId, HUID associationId, DeletionMeta meta) {
		var association = getOrThrow(organizationId, associationId);
		
		association.delete(meta);
		
		repository.save(organizationId, association);
	}
	
	public Optional<MemberAssociation> get(HUID organizationId, HUID associationId) {
		return repository.findById(organizationId, associationId);
	}
	
	public MemberAssociation getOrThrow(HUID organizationId, HUID associationId) {
		return get(organizationId, associationId)
				.orElseThrow(() -> new BlacknodeException("Member association with ID " + associationId + " not found."));
	}
	
	public Optional<MemberAssociation> get(HUID organizationId, HUID memberId, HUID scopeId, MemberAssociation.Scope scope) {
		var association = repository.findByMemberIdAndScopeIdAndScope(organizationId, memberId, scopeId, scope);
		
		return association;
	}
	
	public MemberAssociation getOrThrow(HUID organizationId, HUID memberId, HUID scopeId, MemberAssociation.Scope scope) {
		return repository.findByMemberIdAndScopeIdAndScope(organizationId, memberId, scopeId, scope)
				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role association in scope " + scope));
	}
	
	public List<MemberAssociation> get(HUID organizationId, HUID memberId, MemberAssociation.Scope scope) {
		var associations = repository.findByMemberIdAndScope(organizationId, memberId, scope);
		
		return associations;
	}
	
	public Optional<MemberAssociation> getMemberOrganizationAssociation(HUID organizationId, HUID memberId) {
		return get(organizationId, memberId, organizationId, MemberAssociation.Scope.ORGANIZATION);
	}
	
	public Optional<MemberAssociation> getMemberProjectAssociation(HUID organizationId, HUID memberId, HUID projectId) {
		return get(organizationId, memberId, projectId, MemberAssociation.Scope.PROJECT);
	}
	
	public Optional<MemberAssociation> getMemberChannelAssociation(HUID organizationId, HUID memberId, HUID channelId) {
		return get(organizationId, memberId, channelId, MemberAssociation.Scope.CHANNEL);
	}
	
	public MemberAssociation getMemberOrganizationAssociationOrThrow(HUID organizationId, HUID memberId) {
		return getOrThrow(organizationId, memberId, organizationId, MemberAssociation.Scope.ORGANIZATION);
	}
	
	public MemberAssociation getMemberProjectAssociationOrThrow(HUID organizationId, HUID memberId, HUID projectId) {
		return getOrThrow(organizationId, memberId, projectId, MemberAssociation.Scope.PROJECT);
	}
	
	public MemberAssociation getMemberChannelAssociationOrThrow(HUID organizationId, HUID memberId, HUID channelId) {
		return getOrThrow(organizationId, memberId, channelId, MemberAssociation.Scope.CHANNEL);
	}
	
	public List<MemberAssociation> getMemberAssociationsByRole(HUID organizationId, HUID roleId) {
		return repository.findByRoleId(organizationId, roleId);
	}
	
//	public Role getMemberRoleInOrganizationOrThrow(HUID memberId, HUID organizationId) {
//		return getMemberRoleInOrganization(memberId, organizationId, null)
//				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role in organization with ID " + organizationId));
//	}
//	
//	private Optional<Role> getMemberRoleInOrganization(HUID memberId, HUID organizationId, Member member) {
//		if(member == null) member = memberService.getOrThrow(memberId);
//	
//		member.ensureBelongsToOrganization(organizationId);
//		
//		var association = findAssociation(memberId, organizationId, MemberAssociation.Scope.ORGANIZATION);
//		
//		return association.flatMap(a -> roleService.getById(a.getRoleId()));
//	}
//	
//	public Role getMemberRoleInProjectOrThrow(HUID memberId, HUID projectId) {
//		return getMemberRoleInProject(memberId, projectId, null).orElseThrow(() -> 
//			new BlacknodeException("Member with ID " + memberId + " has no role in project with ID " + projectId));
//	}
//	
//	private Optional<Role> getMemberRoleInProject(HUID memberId, HUID projectId, Member member) {
//		if(member == null) member =  memberService.getOrThrow(memberId);
//		
//		var project = projectService.getOrThrow(projectId);
//		project.ensureBelongsToOrganization(member.getOrganizationId());
//		
//		/* check if superior role (organization) is a privileged role */
//		
//		var organizationRole = getMemberRoleInOrganization(memberId, member.getOrganizationId(), member);
//		
//		if(organizationRole.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false)) {
//			return organizationRole;
//		}
//		
//		var association = findAssociation(memberId, projectId, MemberAssociation.Scope.PROJECT);
//		
//		return association.flatMap(a -> roleService.getById(a.getRoleId()));
//	}
//	
//	public Role getMemberRoleInChannelOrThrow(HUID memberId, HUID channelId) {
//		return getMemberRoleInChannel(memberId, channelId)
//				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role in channel with ID " + channelId));
//	}
//	
//	public Optional<Role> getMemberRoleInChannel(HUID memberId, HUID channelId) {
//		var member = memberService.getOrThrow(memberId);
//		
//		var channel = channelService.getOrThrow(channelId);
//		channel.ensureBelongsToOrganization(member.getOrganizationId());
//		
//		/* check if superior role (project/organization) is a privileged role */
//		var projectRole = getMemberRoleInProject(memberId, channel.getProjectId(), member);
//		
//		if(projectRole.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false)) {
//			return projectRole;
//		}
//		
//		var association = findAssociation(memberId, channelId, MemberAssociation.Scope.CHANNEL);
//		
//		return association.flatMap(a -> roleService.getById(a.getRoleId()));
//	}
//	
//	public void setOrganizationRoleToMember(HUID memberId, HUID roleId, HUID organizationId) {
//		var organization = organizationService.getOrThrow(organizationId);
//		
//		var member = memberService.getOrThrow(memberId);
//		member.ensureBelongsToOrganization(organizationId);
//		
//		var role = roleService.getByIdOrThrow(roleId);
//		role.ensureBelongsToOrganization(organizationId);
//		role.ensureHasScope(Role.Scope.ORGANIZATION);
//		
//		removeAssociationIfExists(memberId, organizationId, MemberAssociation.Scope.ORGANIZATION);
//		
//		var newAssociationMeta = MemberOrganizationAssociationCreationMeta.builder()
//				.memberId(memberId)
//				.roleId(roleId)
//				.organizationId(organizationId)
//				.build();
//		
//		var newAssociation = new MemberAssociation();
//		
//		newAssociation.create(newAssociationMeta);
//		
//		repository.save(newAssociation);
//	}
//	
//	public void setProjectRoleToMember(HUID memberId, HUID roleId, HUID projectId) {
//		var member = memberService.getOrThrow(memberId);
//
//		var organizationId = member.getOrganizationId();
//		
//		var role = roleService.getByIdOrThrow(roleId);
//		role.ensureBelongsToOrganization(organizationId);
//		role.ensureHasScope(Role.Scope.PROJECT);
//		
//		var project = projectService.getOrThrow(projectId);
//		project.ensureBelongsToOrganization(organizationId);
//		
//		removeAssociationIfExists(memberId, projectId, MemberAssociation.Scope.PROJECT);
//		
//		var newAssociationMeta = MemberProjectAssociationCreationMeta.builder()
//				.memberId(memberId)
//				.roleId(roleId)
//				.projectId(projectId)
//				.build();
//		
//		var newAssociation = new MemberAssociation();
//		
//		newAssociation.create(newAssociationMeta);
//		
//		repository.save(newAssociation);
//		
//	}
//	
//	public void setChannelRoleToMember(HUID memberId, HUID roleId, HUID channelId) {
//		var member = memberService.getOrThrow(memberId);
//
//		var organizationId = member.getOrganizationId();
//		
//		var role = roleService.getByIdOrThrow(roleId);
//		role.ensureBelongsToOrganization(organizationId);
//		role.ensureHasScope(Role.Scope.CHANNEL);
//		
//		var channel = channelService.getOrThrow(channelId);
//		channel.ensureBelongsToOrganization(organizationId);
//		
//		removeAssociationIfExists(memberId, channelId, MemberAssociation.Scope.CHANNEL);
//		
//		var newAssociationMeta = MemberChannelAssociationCreationMeta.builder()
//				.memberId(memberId)
//				.roleId(roleId)
//				.channelId(channelId)
//				.build();
//		
//		var newAssociation = new MemberAssociation();
//		
//		newAssociation.create(newAssociationMeta);
//		
//		repository.save(newAssociation);
//		
//	}
//	
//	public List<MemberAssociation> findAssociations(HUID memberId, MemberAssociation.Scope scope) {
//		var associations = repository.findByMemberId(memberId);
//		
//		return associations.stream()
//			.filter(assoc -> memberId.equals(assoc.getMemberId()))
//			.filter(assoc -> assoc.getMeta().getScope() == scope)
//			.toList();
//	}
//	
//	public Optional<MemberAssociation> findAssociation(HUID memberId, HUID scopeId, MemberAssociation.Scope scope) {
//		var associations = repository.findByMemberId(memberId);
//		
//		return associations.stream()
//			.filter(assoc -> memberId.equals(assoc.getMemberId()))
//			.filter(assoc -> assoc.getMeta().getScope() == scope)
//			.filter(assoc -> assoc.getScopeId().equals(scopeId))
//			.findFirst();
//	}
//	
//	public List<MemberAssociation> findAssociationsWithIds(HUID memberId, List<HUID> scopeIds, MemberAssociation.Scope scope) {
//		var associations = repository.findByMemberId(memberId);
//		
//		return associations.stream()
//			.filter(assoc -> memberId.equals(assoc.getMemberId()))
//			.filter(assoc -> assoc.getMeta().getScope() == scope)
//			.filter(assoc -> scopeIds.contains(assoc.getScopeId()))
//			.toList();
//	}
//	
//	public MemberAssociation findAssociationOrThrow(HUID memberId, HUID scopeId, MemberAssociation.Scope scope) {
//		return findAssociation(memberId, scopeId, scope)
//				.orElseThrow(() -> new BlacknodeException("Member with ID " + memberId + " has no role association in scope " + scope));
//	}
//	
//	public List<MemberAssociation> findAssociationsOrThrow(HUID memberId, List<HUID> scopeIds, MemberAssociation.Scope scope) {
//		var associations = findAssociationsWithIds(memberId, scopeIds, scope);
//		
//		if(associations.size() != scopeIds.size()) {
//			throw new BlacknodeException("Member with ID " + memberId + " has no role association in all specified scopes " + scope);
//		}
//		
//		return associations;
//	}
//	
//		
//	private void removeAssociationIfExists(HUID memberId, HUID scopeId, MemberAssociation.Scope scope) {
//		var currentAssociation = findAssociation(memberId, scopeId, scope);
//		
//		if(currentAssociation.isPresent()) {
//			var assoc = currentAssociation.get();
//		
//			var assocDeletionMeta = MemberAssociationDeletionMeta.builder()
//					.build();
//			
//			assoc.delete(assocDeletionMeta);
//			
//			repository.save(assoc);
//		}
//	}
//	
//	public List<HUID> filterAssociatedProjectIds(HUID memberId, List<HUID> projectIds) {
//		var associations = findAssociationsWithIds(memberId, projectIds, MemberAssociation.Scope.PROJECT);
//		
//		var associatedProjectIds = associations.stream()
//				.map(assoc -> assoc.getScopeId())
//				.toList();
//		
//		return associatedProjectIds;
//	}
//	
//	public List<Project> filterAccessibleProjects(HUID memberId, List<Project> projects) {
//		var associations = filterAssociatedProjects(memberId, projects);
//		
//		// access logic can be added here in the future
//		
//		return associations;
//	}
//	
//	public List<Channel> filterAccessibleChannels(HUID memberId, List<Channel> channels) {
//		var associations = filterAssociatedChannels(memberId, channels);
//		
//		// access logic can be added here in the future
//		
//		return associations;
//	}
//	
//	public List<HUID> filterAccessibleProjectIds(HUID memberId, List<HUID> projectIds) {
//		var associations = filterAssociatedProjectIds(memberId, projectIds);
//		
//		// access logic can be added here in the future
//		
//		return associations;
//	}
//	
//	public List<HUID> filterAccessibleChannelIds(HUID memberId, List<HUID> channelIds) {
//		var associations = filterAssociatedChannelIds(memberId, channelIds);
//		
//		// access logic can be added here in the future
//		
//		return associations;
//	}
//	
//	public List<Project> filterAssociatedProjects(HUID memberId, List<Project> projects) {
//		var associations = findAssociations(memberId, MemberAssociation.Scope.PROJECT);
//		
//		var associatedProjectIds = associations.stream()
//				.map(assoc -> assoc.getScopeId())
//				.toList();
//		
//		return projects.stream()
//				.filter(project -> associatedProjectIds.contains(project.getId()))
//				.toList();
//	}
//	
//	public List<HUID> filterAssociatedChannelIds(HUID memberId, List<HUID> projectIds) {
//		var associations = findAssociationsWithIds(memberId, projectIds, MemberAssociation.Scope.PROJECT);
//		
//		var associatedProjectIds = associations.stream()
//				.map(assoc -> assoc.getScopeId())
//				.toList();
//		
//		return associatedProjectIds;
//	}
//	
//	public List<Channel> filterAssociatedChannels(HUID memberId, List<Channel> channels) {	
//		var associations = findAssociations(memberId, MemberAssociation.Scope.CHANNEL);
//		
//		var associatedChannelIds = associations.stream()
//				.map(assoc -> assoc.getScopeId())
//				.toList();
//		
//		return channels.stream()
//				.filter(channel -> associatedChannelIds.contains(channel.getId()))
//				.toList();
//	}
//	
//	public void ensureMemberCanAccessProject(HUID memberId, HUID projectId) {
//		if(!isMemberInProject(memberId, projectId)) {
//			throw new BlacknodeException("Member with ID " + memberId + " has no access to project with ID " + projectId);
//		}
//	}
//	
//	public void ensureMemberCanAccessChannel(HUID memberId, HUID channelId) {
//		if(!isMemberInChannel(memberId, channelId)) {
//			throw new BlacknodeException("Member with ID " + memberId + " has no access to channel with ID " + channelId);
//		}
//	}
//	
//	public void ensureMemberCanAccessOrganization(HUID memberId, HUID organizationId) {
//		if(!isMemberInOrganization(memberId, organizationId)) {
//			throw new BlacknodeException("Member with ID " + memberId + " has no access to organization with ID " + organizationId);
//		}
//	}
//	
//	public void ensureMemberCanAccessProjects(HUID memberId, List<HUID> projectIds) {
//		var associations = findAssociationsWithIds(memberId, projectIds, MemberAssociation.Scope.PROJECT);
//		
//		if(associations.size() != projectIds.size()) {
//			throw new BlacknodeException("Member with ID " + memberId + " has no access to all specified projects.");
//		}
//	}
//	
//	public void ensureMemberCanAccessChannels(HUID memberId, List<HUID> channelIds) {
//		var associations = findAssociationsWithIds(memberId, channelIds, MemberAssociation.Scope.CHANNEL);
//	
//		if(associations.size() != channelIds.size()) {
//			throw new BlacknodeException("Member with ID " + memberId + " has no access to all specified channels.");
//		}
//	}
//	
//	public void ensureMemberHavingSuperRoleInOrganization(HUID memberId, HUID organizationId) {
//		if(!isMemberHavingSuperRoleInOrganization(memberId, organizationId)) {
//			throw new BlacknodeException("Member with ID " + memberId + " does not have a super privileged role in organization with ID " + organizationId);
//		}
//	}
//	
//	public void ensureMemberHavingSuperRoleInProject(HUID memberId, HUID projectId) {
//		if(!isMemberHavingSuperRoleInProject(memberId, projectId)) {
//			throw new BlacknodeException("Member with ID " + memberId + " does not have a super privileged role in project with ID " + projectId);
//		}
//	}
//	
//	public void ensureMemberHavingSuperRoleInChannel(HUID memberId, HUID channelId) {
//		if(!isMemberHavingSuperRoleInChannel(memberId, channelId)) {
//			throw new BlacknodeException("Member with ID " + memberId + " does not have a super privileged role in channel with ID " + channelId);
//		}
//	}
//	
//	public boolean isMemberHavingSuperRoleInOrganization(HUID memberId, HUID organizationId) {
//		var role = getMemberRoleInOrganization(memberId, organizationId, null);
//		
//		return role.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false);
//	}
//	
//	public boolean isMemberHavingSuperRoleInProject(HUID memberId, HUID projectId) {
//		var role = getMemberRoleInProject(memberId, projectId, null);
//		
//		return role.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false);
//	}
//	
//	public boolean isMemberHavingSuperRoleInChannel(HUID memberId, HUID channelId) {
//		var role = getMemberRoleInChannel(memberId, channelId);
//		
//		return role.map(Role::getMeta).map(RoleMeta::isSuperPrivileged).orElse(false);
//	}
//	
//	public boolean isMemberInOrganization(HUID memberId, HUID organizationId) {
//		return getMemberRoleInOrganization(memberId, organizationId, null).isPresent();
//	}
//	
//	public boolean isMemberInProject(HUID memberId, HUID projectId) {
//		return getMemberRoleInProject(memberId, projectId, null).isPresent();
//	}
//	
//	public boolean isMemberInChannel(HUID memberId, HUID channelId) {
//		return getMemberRoleInChannel(memberId, channelId).isPresent();
//	}
}
