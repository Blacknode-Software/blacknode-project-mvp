package software.blacknode.backend.application.role.command;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class RoleCreateCommand implements ExecutionCommand {

	private final String name;
	private final String description;
	private final String color;
	
}
