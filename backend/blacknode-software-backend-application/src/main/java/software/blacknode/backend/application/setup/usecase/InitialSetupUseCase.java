package software.blacknode.backend.application.setup.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.auth.AuthService;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.setup.command.InitialSetupCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.account.meta.create.AccountInitialAdminCreationMeta;
import software.blacknode.backend.domain.auth.meta.create.AuthByPasswordCreationMeta;
import software.blacknode.backend.domain.channel.meta.create.ChannelInitialCreationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.meta.create.MemberOrganizationAssociationCreationMeta;
import software.blacknode.backend.domain.member.meta.create.MemberAdminCreationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;
import software.blacknode.backend.domain.project.meta.create.ProjectInitialCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleInitialOrganizationScopeCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleInitialProjectScopeCreationMeta;

@Service
@RequiredArgsConstructor
public class InitialSetupUseCase implements ResultExecutionUseCase<InitialSetupCommand, InitialSetupUseCase.Result> {

	private final MemberAssociationService memberAssociationService;
	private final OrganizationService organizationService;
	private final ProjectService projectService;
	private final ChannelService channelService;
	private final AccountService accountService;
	private final RoleService roleService;
	private final MemberService memberService;
	private final AuthService authService;
	
	@Override
	@Transactional
	public InitialSetupUseCase.Result execute(InitialSetupCommand command) {
		if(organizationService.isDefaultOrganizationPresent()) {
			throw new BlacknodeException("Initial setup has already been completed.");
		}

		var organizationMeta = OrganizationInitialCreationMeta.builder()
				.name(command.getOrganizationName())
				.build();
		
		var organization = organizationService.create(organizationMeta);
		
		var organizationId = organization.getId();
		
		var adminOrgRoleMeta = RoleInitialOrganizationScopeCreationMeta.builder()
				.name("Admin")
				.description("Default organization admin role with full permissions")
				.color("#C12566")
				.build();
		
		var adminOrgRole = roleService.create(organizationId, adminOrgRoleMeta);
		
		var memberOrgRoleMeta = RoleInitialOrganizationScopeCreationMeta.builder()
				.name("Organization Member")
				.description("Default organization member role with standard permissions")
				.color("#FAFAFF")
				.byDefaultAssigned(true)
				.build();
		
		var memberOrgRole = roleService.create(organizationId, memberOrgRoleMeta);
		
		var pmProjRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.name("Project Manager")
				.description("Default project manager role with project management permissions")
				.color("#E6AD6E")
				.build();

		var pmProjRole = roleService.create(organizationId, pmProjRoleMeta);
		
		var memberProjRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.name("Project Member")
				.description("Default project member role with standard permissions")
				.color("#FAFAFF")
				.byDefaultAssigned(true)
				.build();
		
		var memberProjRole = roleService.create(organizationId, memberProjRoleMeta);

		var leadChnlRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.name("Lead")
				.description("Default lead channel role with elevated permissions")
				.color("#5B80DA")
				.build();
		
		var leadChnlRole = roleService.create(organizationId, leadChnlRoleMeta);

		var memberChnlRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.name("Channel Member")
				.description("Default member channel role with standard permissions")
				.color("#FAFAFF")
				.byDefaultAssigned(true)
				.build();
	
		var memberChnlRole = roleService.create(organizationId, memberChnlRoleMeta);
		
		var accountMeta = AccountInitialAdminCreationMeta.builder()
				.email(command.getAdminEmail())
				.firstName(command.getAdminFirstName())
				.lastName(command.getAdminLastName())
				.build();
		
		var account = accountService.create(accountMeta);
		
		var authMeta = AuthByPasswordCreationMeta.builder()
				.password(command.getAdminPassword())
				.accountId(account.getId())
				.build();
		
		var auth = authService.create(authMeta);
		
		var memberMeta = MemberAdminCreationMeta.builder()
				.accountId(account.getId())
				.build();
		
		var member = memberService.create(organizationId, memberMeta);
		
		var associationMeta = MemberOrganizationAssociationCreationMeta.builder()
				.memberId(member.getId())
				.organizationId(organizationId)
				.build();
		
		var association = memberAssociationService.create(organizationId, associationMeta);
		
		/* Base initial project and channel setup */
		if(command.isInitialProjectConfiguration()) {
			 
			var projectMeta = ProjectInitialCreationMeta.builder()
					.name("Initial Project")
					.description("This is your first project. You can modify or delete it.")
					.build();
			
			var project = projectService.create(organizationId, projectMeta);
			
			var channelMeta_0 = ChannelInitialCreationMeta.builder()
					.name("General")
					.description("Default channel for general tasks")
					.projectId(project.getId())
					.build();
			
			var channel_0 = channelService.create(organizationId, channelMeta_0);
			
			var channelMeta_1 = ChannelInitialCreationMeta.builder()
					.name("Development")
					.description("Channel for development tasks")
					.projectId(project.getId())
					.color("#DDEFFA")
					.build();
			
			var channel_1 = channelService.create(organizationId, channelMeta_1);
			
			var channelMeta_2 = ChannelInitialCreationMeta.builder()
					.name("Design")
					.description("Channel for design tasks")
					.projectId(project.getId())
					.color("#FFF0DD")
					.build();
			
			var channel_2 = channelService.create(organizationId, channelMeta_2);
		}
		
		return Result.builder()
				.organizationId(organization.getId())
				.build();
	}

	@Getter 
	@Builder
	@AllArgsConstructor
	public static class Result {
		private final HUID organizationId;
	}
}