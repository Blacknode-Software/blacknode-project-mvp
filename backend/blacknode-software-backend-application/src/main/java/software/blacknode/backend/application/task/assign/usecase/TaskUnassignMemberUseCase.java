package software.blacknode.backend.application.task.assign.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TaskUnassignMemberCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.assign.meta.delete.impl.TaskUnassignDeletionMeta;

@Service
@RequiredArgsConstructor
public class TaskUnassignMemberUseCase implements ExecutionUseCase<TaskUnassignMemberCommand> {

	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final TaskService taskService;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(TaskUnassignMemberCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskId = command.getTaskId();
		var task = taskService.getOrThrow(organizationId, taskId);
		
		var assigneeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assigneeId);
		
		taskAccessControl.ensureMemberHasTaskAccess(memberId, task, AccessLevel.WRITE);
		
		// taskAccessControl.ensureMemberHasTaskAccess(assignee, task, AccessLevel.READ);
		
		var currentAssignment = taskAssignService.getByMemberIdAndTaskId(organizationId, taskId, assigneeId)
				.orElseThrow(() -> new BlacknodeException("Member with id " + assigneeId + " is not assigned to task with id " + taskId));
		
		var meta = TaskUnassignDeletionMeta.builder()
				.build();
		
		taskAssignService.delete(organizationId, currentAssignment.getId(), meta);
	}

}
