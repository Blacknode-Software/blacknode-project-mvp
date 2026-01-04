package software.blacknode.backend.application.task.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.command.TaskFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.Task;

@Service
@RequiredArgsConstructor
public class TaskFetchUseCase implements ResultExecutionUseCase<TaskFetchCommand, TaskFetchUseCase.Result> {

	private final AccessControlService accessControlService;
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(TaskFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskId = command.getTaskId();
		
		accessControlService.ensureMemberHasTaskAccess(organizationId, memberId, 
				taskId, AccessLevel.READ);
		
		var task = taskService.getOrThrow(organizationId, taskId);
		
		return Result.builder()
				.task(task)
				.build();
	}

	@Builder
	@Getter
	@ToString
	public static class Result {
		
		@NonNull
		private final Task task;
		
	}

}
