package software.blacknode.backend.application.project.command;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.impl.PatchExecutionCommand;

@Getter
@SuperBuilder
@ToString
public class ProjectPatchCommand extends PatchExecutionCommand {
	
	private final String name;
	private final String description;
	private final String color;
	
	private final HUID id;
}
