package software.blacknode.backend.application.auth.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class AuthenticateWithPasswordCommand implements ExecutionCommand {

	@NonNull 
	@Email
	private String email;
	
	@NonNull
	@Size(min = 8)
	private String password;
	
}
