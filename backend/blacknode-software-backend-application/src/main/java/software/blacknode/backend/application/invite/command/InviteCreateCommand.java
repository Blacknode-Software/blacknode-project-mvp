package software.blacknode.backend.application.invite.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class InviteCreateCommand implements ExecutionCommand {

	private String email;
	
}
