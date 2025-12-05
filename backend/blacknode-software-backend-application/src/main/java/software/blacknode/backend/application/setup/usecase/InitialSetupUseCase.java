package software.blacknode.backend.application.setup.usecase;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.setup.command.InitialSetupCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;

@Service
@RequiredArgsConstructor
public class InitialSetupUseCase implements ResultExecutionUseCase<InitialSetupCommand, InitialSetupUseCase.Result> {

	private final OrganizationService organizationService;
	
	@Override
	public InitialSetupUseCase.Result execute(InitialSetupCommand command) {
		// Check if default organization already exists?
		// If yes, throw exception
		
		// Create default organization
		
		OrganizationInitialCreationMeta organizationMeta = new OrganizationInitialCreationMeta(
				command.getOrganizationName()
		);
		
		var organization = organizationService.create(organizationMeta);
		
		// Create default roles, projects, admin user, etc.
		
		// Create admin account and member
		
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