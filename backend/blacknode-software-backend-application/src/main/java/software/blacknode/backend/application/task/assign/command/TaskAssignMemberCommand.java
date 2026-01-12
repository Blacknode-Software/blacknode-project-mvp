package software.blacknode.backend.application.task.assign.command;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class TaskAssignMemberCommand implements ExecutionCommand {

	private final HUID taskId;
	
	private final HUID memberId;
	
}
