package software.blacknode.backend.application.organization.command;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.application.command.impl.PatchExecutionCommand;

@Getter
@SuperBuilder
@ToString
public class OrganizationPatchCommand extends PatchExecutionCommand {

	private String name;
	
}
