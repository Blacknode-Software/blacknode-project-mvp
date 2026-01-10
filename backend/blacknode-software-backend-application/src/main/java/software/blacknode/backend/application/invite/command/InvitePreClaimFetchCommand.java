package software.blacknode.backend.application.invite.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class InvitePreClaimFetchCommand implements ExecutionCommand {

	private String token;
	
}
