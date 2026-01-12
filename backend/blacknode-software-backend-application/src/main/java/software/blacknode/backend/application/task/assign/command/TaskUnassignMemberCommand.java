package software.blacknode.backend.application.task.assign.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@RequiredArgsConstructor
@ToString
public class TaskUnassignMemberCommand implements ExecutionCommand {

	private final HUID taskId;
	private final HUID memberId;
	
}
