package software.blacknode.backend.application.setup.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@AllArgsConstructor
public class InitialSetupCommand implements ExecutionCommand {

	private String organizationName;
	
	private String adminFirstName;
	private String adminLastName;
	private String adminEmail;
	private String adminPassword;
}
