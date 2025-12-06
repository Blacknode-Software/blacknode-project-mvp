package software.blacknode.backend.application.setup.usecase;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.auth.AuthService;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.setup.command.InitialSetupCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.account.meta.create.AccountInitialAdminCreationMeta;
import software.blacknode.backend.domain.auth.meta.create.AuthByPasswordCreationMeta;
import software.blacknode.backend.domain.member.meta.create.MemberAdminCreationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;

@Service
@RequiredArgsConstructor
public class InitialSetupUseCase implements ResultExecutionUseCase<InitialSetupCommand, InitialSetupUseCase.Result> {

	private final OrganizationService organizationService;
	private final AccountService accountService;
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
				.roleId(organization.getDefaultAdminRoleId())
				.build();
		
		
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