package software.blacknode.backend.application.setup.usecase;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.auth.AuthService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.setup.command.InitialSetupCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.account.meta.create.AccountInitialAdminCreationMeta;
import software.blacknode.backend.domain.auth.meta.create.AuthByPasswordCreationMeta;
import software.blacknode.backend.domain.member.meta.create.MemberAdminCreationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleInitialOrganizationScopeCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleInitialProjectScopeCreationMeta;

@Service
@RequiredArgsConstructor
public class InitialSetupUseCase implements ResultExecutionUseCase<InitialSetupCommand, InitialSetupUseCase.Result> {

	private final OrganizationService organizationService;
	private final AccountService accountService;
	private final RoleService roleService;
	private final MemberService memberService;
	private final AuthService authService;
	
	@Override
	public InitialSetupUseCase.Result execute(InitialSetupCommand command) {
		// Check if default organization already exists?
		// If yes, throw exception
		
		// Create default organization
		
		var organizationMeta = OrganizationInitialCreationMeta.builder()
				.name(command.getOrganizationName())
				.build();
		
		var organization = organizationService.create(organizationMeta);
		
		var adminOrgRoleMeta = RoleInitialOrganizationScopeCreationMeta.builder()
				.organizationId(organization.getId())
				.name("Admin")
				.description("Default organization admin role with full permissions")
				.color("#C12566")
				.build();
		
		var memberOrgRoleMeta = RoleInitialOrganizationScopeCreationMeta.builder()
				.organizationId(organization.getId())
				.name("Member")
				.description("Default organization member role with standard permissions")
				.color("#FAFAFF")
				.byDefaultAssigned(true)
				.build();
		
		var pmProjRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.organizationId(organization.getId())
				.name("Project Manager")
				.description("Default project manager role with project management permissions")
				.color("#E6AD6E")
				.build();
		
		var memberProjRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.organizationId(organization.getId())
				.name("Member")
				.description("Default project member role with standard permissions")
				.color("#FAFAFF")
				.byDefaultAssigned(true)
				.build();
		
		var leadChnlRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.organizationId(organization.getId())
				.name("Lead")
				.description("Default lead channel role with elevated permissions")
				.color("#5B80DA")
				.build();
		
		var memberChnlRoleMeta = RoleInitialProjectScopeCreationMeta.builder()
				.organizationId(organization.getId())
				.name("Member")
				.description("Default member channel role with standard permissions")
				.color("#FAFAFF")
				.byDefaultAssigned(true)
				.build();
		
		var adminOrgRole = roleService.create(adminOrgRoleMeta);
		var memberOrgRole = roleService.create(memberOrgRoleMeta);
		
		var pmProjRole = roleService.create(pmProjRoleMeta);
		var memberProjRole = roleService.create(memberProjRoleMeta);
		
		var leadChnlRole = roleService.create(leadChnlRoleMeta);
		var memberChnlRole = roleService.create(memberChnlRoleMeta);
		
		// Create default roles, projects, admin user, etc.
		
		// Create admin account and member
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
				.organizationId(organization.getId())
				.build();
		
		var member = memberService.create(memberMeta);
		
		
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