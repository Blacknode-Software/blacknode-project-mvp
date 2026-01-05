package software.blacknode.backend.application.auth.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class AuthenticateWithPasswordCommand implements ExecutionCommand {

	@NonNull 
	private String email;
	
	@NonNull
	private String password;
	
}
