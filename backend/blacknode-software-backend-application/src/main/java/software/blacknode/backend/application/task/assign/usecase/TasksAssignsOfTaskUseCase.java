package software.blacknode.backend.application.task.assign.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfTaskCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class TasksAssignsOfTaskUseCase implements ResultExecutionUseCase<TasksAssignsOfTaskCommand, TasksAssignsOfTaskUseCase.Result> {
	
	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(TasksAssignsOfTaskCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskId = command.getTaskId();
		var task = taskService.getOrThrow(organizationId, taskId);
		
		taskAccessControl.ensureMemberHasTaskAccess(memberId, task, AccessLevel.READ);
		
		var assignments = taskAssignService.getByTaskId(organizationId, taskId)
				.stream()
				.map(ta -> ta.getId())
				.toList();
		
		return Result.builder()
				.taskAssignsIds(assignments)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		private final List<HUID> taskAssignsIds;
		
	}

}
