package software.blacknode.backend.application.usecase;

import software.blacknode.backend.application.command.ExecutionCommand;

public interface ExecutionUseCase<C extends ExecutionCommand> {
	
	void execute(C command);
	
}
