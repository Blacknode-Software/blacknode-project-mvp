package software.blacknode.backend.application.task.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TasksBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.task.Task;

@Service
@RequiredArgsConstructor
public class TasksBatchFetchUseCase implements ResultExecutionUseCase<TasksBatchFetchCommand, TasksBatchFetchUseCase.Result> {

	private final AccessControlService accessControlService;
	private final TaskService taskService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public Result execute(TasksBatchFetchCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		var taskIds = command.getTaskIds();
		
		var tasks = taskService.getByIds(organizationId, taskIds);
		
		tasks = tasks.stream()
				.filter(task -> accessControlService.hasAccessToTask(organizationId, memberId, task.getId(), 
						AccessControlService.AccessLevel.READ))
				.toList();
		
		return Result.builder()
				.tasks(tasks)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private List<Task> tasks;
		
	}

}
