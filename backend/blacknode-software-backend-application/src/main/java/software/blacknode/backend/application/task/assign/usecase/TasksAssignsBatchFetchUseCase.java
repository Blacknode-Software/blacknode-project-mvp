package software.blacknode.backend.application.task.assign.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TasksAssignsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.assign.TaskAssign;

@Service
@RequiredArgsConstructor
public class TasksAssignsBatchFetchUseCase implements ResultExecutionUseCase<TasksAssignsBatchFetchCommand, TasksAssignsBatchFetchUseCase.Result> {

	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(TasksAssignsBatchFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var member = memberService.getOrThrow(organizationId, memberId);
		
		var assignIds = command.getAssignIds();
		
		var assignments = taskAssignService.getByIds(organizationId, assignIds)
				.stream()
				.filter(ta -> taskAccessControl.hasAccessToTask(member, ta.getTaskId(), AccessLevel.READ))
				.toList();
		
		return Result.builder()
				.taskAssigns(assignments)
				.build();
	}

	@Builder
	@Getter
	@ToString
	public static class Result {

		private final List<TaskAssign> taskAssigns;
		
	}

}
