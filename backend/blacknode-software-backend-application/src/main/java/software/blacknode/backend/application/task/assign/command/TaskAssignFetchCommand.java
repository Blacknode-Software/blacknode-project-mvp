package software.blacknode.backend.application.task.assign.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Builder
@Getter
@ToString
public class TaskAssignFetchCommand implements ExecutionCommand {	

	private final HUID assignId;
	
}
