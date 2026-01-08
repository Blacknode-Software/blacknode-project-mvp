package software.blacknode.backend.application.task.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TasksBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.Task;

@Service
@RequiredArgsConstructor
public class TasksBatchFetchUseCase implements ResultExecutionUseCase<TasksBatchFetchCommand, TasksBatchFetchUseCase.Result> {

	private final TaskAccessControl taskAccessControl;
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(TasksBatchFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskIds = command.getTaskIds();
		
		var tasks = taskService.getByIds(organizationId, taskIds)
				.stream()
				.filter(task -> taskAccessControl.hasAccessToTask(memberId, task, 
						AccessLevel.READ))
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
