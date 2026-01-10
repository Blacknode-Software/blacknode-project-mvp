package software.blacknode.backend.application.invite.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class InviteClaimCommand implements ExecutionCommand {

	private final String token;
	
	private final String firstName;

	private final String lastName;
	
	private final String password;
	
}
