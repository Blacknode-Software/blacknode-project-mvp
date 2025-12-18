package software.blacknode.backend.application.command.impl;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@SuperBuilder
public class PatchExecutionCommand implements ExecutionCommand {

	@Builder.Default
	private List<String> operations = List.of();
	
}
