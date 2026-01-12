package software.blacknode.backend.application.task.assign.usecase;

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
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TaskAssignFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.assign.TaskAssign;

@Service
@RequiredArgsConstructor
public class TaskAssignFetchUseCase implements ResultExecutionUseCase<TaskAssignFetchCommand, TaskAssignFetchUseCase.Result> {

	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(TaskAssignFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var assignId = command.getAssignId();
		var assignment = taskAssignService.getOrThrow(organizationId, assignId);
		
		var taskId = assignment.getTaskId();
		
		taskAccessControl.ensureMemberHasTaskAccess(organizationId, memberId, taskId, AccessLevel.READ);
		
		return Result.builder()
				.assign(assignment)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {

		@NonNull
		private final TaskAssign assign;
		
	}

}
