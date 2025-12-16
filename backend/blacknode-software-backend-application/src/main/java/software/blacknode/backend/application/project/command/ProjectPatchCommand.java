package software.blacknode.backend.application.project.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.impl.PatchExecutionCommand;

@Getter
@SuperBuilder
public class ProjectPatchCommand extends PatchExecutionCommand {

	private final HUID id;
	
	private final String name;
	private final String description;
	private final String color;
	
}
