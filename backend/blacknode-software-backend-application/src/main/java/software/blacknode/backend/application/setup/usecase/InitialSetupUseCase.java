package software.blacknode.backend.application.setup.usecase;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.setup.command.InitialSetupCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;

@Service
public class InitialSetupUseCase implements ResultExecutionUseCase<InitialSetupCommand, InitialSetupUseCase.Result> {

	private OrganizationService organizationService;
	
	public InitialSetupUseCase(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	@Override
	public InitialSetupUseCase.Result execute(InitialSetupCommand command) {
		OrganizationInitialCreationMeta organizationMeta = new OrganizationInitialCreationMeta(
				command.getOrganizationName()
		);

		var organization = organizationService.create(organizationMeta);
		
		return new Result(organization.getId());
	}

	@Getter 
	@AllArgsConstructor
	public static class Result {
		private final HUID organizationId;
	}
}