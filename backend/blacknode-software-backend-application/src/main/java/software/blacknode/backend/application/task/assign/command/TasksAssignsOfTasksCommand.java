package software.blacknode.backend.application.task.assign.command;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class TasksAssignsOfTasksCommand implements ExecutionCommand {

	private List<HUID> taskIds;
	
}
