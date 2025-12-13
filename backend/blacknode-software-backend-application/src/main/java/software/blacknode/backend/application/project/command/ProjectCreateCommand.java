package software.blacknode.backend.application.project.command;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.application.command.ExecutionCommand;


@Builder
@Getter
public class ProjectCreateCommand implements ExecutionCommand {

	private String name;
	private String description;
	private String color;
	
}
