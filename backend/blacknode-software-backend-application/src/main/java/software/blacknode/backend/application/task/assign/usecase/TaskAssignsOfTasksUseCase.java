package software.blacknode.backend.application.task.assign.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TaskAssignsOfTasksCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class TaskAssignsOfTasksUseCase implements ResultExecutionUseCase<TaskAssignsOfTasksCommand, TaskAssignsOfTasksUseCase.Result> {

	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final MemberService memberService;
	
	private final TaskService taskService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(TaskAssignsOfTasksCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var member = memberService.getOrThrow(organizationId, memberId);
		
		var tasksIds = command.getTaskIds();
		
		var assignments = taskAssignService.getByTaskIds(organizationId, tasksIds)
				.stream()
				.filter(ta -> taskAccessControl.hasAccessToTask(member, ta.getTaskId(), AccessLevel.READ))
				.toList();
		
		var assignIds = assignments.stream()
				.map(ta -> ta.getId())
				.toList();
		
		return Result.builder()
				.assignIds(assignIds)
				.build();
	}

	@Builder
	@Getter
	@ToString
	public static class Result {

		private final List<HUID> assignIds;
		
	}

}
