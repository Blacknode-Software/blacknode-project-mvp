package software.blacknode.backend.application.task.assign.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TasksAssignsOfMembersCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class TasksAssignsOfMembersUseCase implements ResultExecutionUseCase<TasksAssignsOfMembersCommand, TasksAssignsOfMembersUseCase.Result> {

	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(TasksAssignsOfMembersCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var member = memberService.getOrThrow(organizationId, memberId);
		
		var assigneesIds = memberService.getByIds(organizationId, command.getMemberIds())
				.stream()
				.map(m -> m.getId())
				.toList();
		
		var assignments = taskAssignService.getByMemberIds(organizationId, assigneesIds)
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

	@Getter
	@Builder
	@RequiredArgsConstructor
	public static class Result {

		private final List<HUID> assignIds;
		
	}

}
