package software.blacknode.backend.application.command.impl;

import java.util.List;

import lombok.experimental.SuperBuilder;
import software.blacknode.backend.application.command.ExecutionCommand;

@SuperBuilder
public class PatchExecutionCommand implements ExecutionCommand {

	protected List<String> patchOperations = List.of();
	
}
