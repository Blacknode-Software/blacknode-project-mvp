package software.blacknode.backend.application.role.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.impl.PatchExecutionCommand;

@Getter
@SuperBuilder
public class RolePatchCommand extends PatchExecutionCommand {

	private final String name;
	
	private final String description;
	
	private final String color;

	@NonNull
	private final HUID id;
	
}
