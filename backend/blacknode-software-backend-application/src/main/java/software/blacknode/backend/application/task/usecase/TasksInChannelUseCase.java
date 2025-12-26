package software.blacknode.backend.application.task.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TasksInChannelCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class TasksInChannelUseCase implements ResultExecutionUseCase<TasksInChannelCommand, TasksInChannelUseCase.Result> {
	
	private final AccessControlService accessControlService;	
	
	private final TaskService taskService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public TasksInChannelUseCase.Result execute(TasksInChannelCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		var channelId = command.getChannelId();
		
		accessControlService.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessControlService.AccessLevel.READ);
		
		var tasks = taskService.getAllInChannel(organizationId, channelId);
		
		var tasksIds = tasks.stream()
				/* By default tasks are shown for all members of the channel - this check is not needed
				.filter(task -> accessControlService.hasAccessToTask(organizationId, memberId, task.getId(), 
						AccessControlService.AccessLevel.READ)) */
				.map(task -> task.getId())
				.toList();
		
		return Result.builder()
				.tasksIds(tasksIds)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<HUID> tasksIds;
		
	}

}
