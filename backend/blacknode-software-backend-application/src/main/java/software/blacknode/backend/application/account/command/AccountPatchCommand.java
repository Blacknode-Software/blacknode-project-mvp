package software.blacknode.backend.application.account.command;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.application.command.impl.PatchExecutionCommand;

@Getter
@SuperBuilder
@ToString
public class AccountPatchCommand extends PatchExecutionCommand {

	private String firstName;
	private String lastName;
	
}
