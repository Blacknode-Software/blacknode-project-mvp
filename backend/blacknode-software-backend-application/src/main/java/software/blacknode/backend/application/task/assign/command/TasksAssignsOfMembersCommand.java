package software.blacknode.backend.application.task.assign.command;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@RequiredArgsConstructor
@ToString
public class TasksAssignsOfMembersCommand implements ExecutionCommand {
	
	private final List<HUID> memberIds;

}
