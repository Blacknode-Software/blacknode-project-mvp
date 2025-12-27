package software.blacknode.backend.application.task.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class TaskDeleteCommand implements ExecutionCommand {

	@NonNull
	private final HUID taskId;
	
}
