package software.blacknode.backend.application.member.role;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.domain.member.role.MemberRoleAssociation;
import software.blacknode.backend.domain.member.role.meta.create.MemberRoleAssociationCreationMeta;
import software.blacknode.backend.domain.member.role.meta.delete.MemberRoleAssociationDeletionMeta;
import software.blacknode.backend.domain.member.role.repository.MemberRoleRepository;
import software.blacknode.backend.domain.role.Role;

@Service
@RequiredArgsConstructor
public class MemberRoleService {

	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final MemberService memberService;
	private final RoleService roleService;
	
	private final MemberRoleRepository memberRoleRepository;
	
	public void setOrganizationRoleToMember(HUID memberId, HUID roleId, HUID organizationId) {
		var organization = organizationService.getOrganizationOrThrow(organizationId);
		
		var member = memberService.getMemberOrThrow(memberId);
		member.ensureBelongsToOrganization(organizationId);
		
		var role = roleService.getRoleOrThrow(roleId);
		role.ensureBelongsToOrganization(organizationId);
		role.ensureHasScope(Role.Scope.ORGANIZATION);
		
		var associations = memberRoleRepository.getByMemberId(memberId);
		
		var currentAssociation = associations.stream()
			.filter(assoc -> assoc.getScopeId().equals(organizationId))
			.findFirst();
		
		if(currentAssociation.isPresent()) {
			var assoc = currentAssociation.get();
		
			var assocDeletionMeta = MemberRoleAssociationDeletionMeta.builder()
					.build();
			
			assoc.delete(assocDeletionMeta);
			
			memberRoleRepository.save(assoc);
		}
		
		var newAssociationMeta = MemberRoleAssociationCreationMeta.builder()
				.memberId(memberId)
				.roleId(roleId)
				.scopeId(organizationId)
				.build();
		
		var newAssociation = new MemberRoleAssociation();
		
		newAssociation.create(newAssociationMeta);
		
		memberRoleRepository.save(newAssociation);
	}
	
	public void setProjectRoleToMember(HUID memberId, HUID roleId, HUID projectId) {
		// TODO Auto-generated method stub
		
	}
	
	public void setChannelRoleToMember(HUID memberId, HUID roleId, HUID channelId) {
		// TODO Auto-generated method stub
		
	}
}
