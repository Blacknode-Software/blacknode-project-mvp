package software.blacknode.backend.application.task.assign.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class TasksAssignsOfMemberCommand implements ExecutionCommand {
	
	private final HUID memberId;

}
