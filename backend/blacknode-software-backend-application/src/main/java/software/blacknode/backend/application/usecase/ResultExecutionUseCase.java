package software.blacknode.backend.application.usecase;

import software.blacknode.backend.application.command.ExecutionCommand;

public interface ResultExecutionUseCase<C extends ExecutionCommand, R> {
	
	 R execute(C command);
	
}
