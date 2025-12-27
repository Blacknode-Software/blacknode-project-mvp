package software.blacknode.backend.application.project.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import software.blacknode.backend.application.command.ExecutionCommand;


@Builder
@Getter
@ToString
public class ProjectCreateCommand implements ExecutionCommand {

	private final String name;
	private final String description;
	private final String color;
	
}
