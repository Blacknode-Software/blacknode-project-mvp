package software.blacknode.backend.api.controller.setup.converter;

import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseRequestConverter;
import software.blacknode.backend.api.controller.setup.request.InitialSetupRequest;
import software.blacknode.backend.application.setup.command.InitialSetupCommand;

@Component
public class InitialSetupRequestConverter implements BaseRequestConverter<InitialSetupRequest, InitialSetupCommand> {

	@Override
	public InitialSetupCommand convert(InitialSetupRequest request) {
		return InitialSetupCommand.builder()
				.organizationName(request.getOrganizationName())
				.adminFirstName(request.getAdminFirstName())
				.adminLastName(request.getAdminLastName())
				.adminEmail(request.getAdminEmail())
				.adminPassword(request.getAdminPassword())
				.build();
	}
	
}
