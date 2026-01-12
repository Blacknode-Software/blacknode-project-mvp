package software.blacknode.backend.application.task.assign.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.application.task.assign.TaskAssignService;
import software.blacknode.backend.application.task.assign.command.TaskAssignMemberCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.task.assign.meta.create.impl.TaskAssignByMemberCreationMeta;

@Service
@RequiredArgsConstructor
public class TaskAssignMemberUseCase implements ExecutionUseCase<TaskAssignMemberCommand> {

	private final TaskAccessControl taskAccessControl;
	
	private final TaskAssignService taskAssignService;
	
	private final TaskService taskService;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(TaskAssignMemberCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskId = command.getTaskId();
		var task = taskService.getOrThrow(organizationId, taskId);
		
		var assigneeId = command.getMemberId();
		var assignee = memberService.getOrThrow(organizationId, assigneeId);
		
		taskAccessControl.ensureMemberHasTaskAccess(memberId, task, AccessLevel.WRITE);
		
		taskAccessControl.ensureMemberHasTaskAccess(assignee, task, AccessLevel.READ);
		
		var currentAssignment = taskAssignService.getByMemberIdAndTaskId(organizationId, taskId, assigneeId);
		
		if(currentAssignment.isPresent()) {
			throw new BlacknodeException("Member with id " + assigneeId + " is already assigned to task with id " + taskId);
		}
		
		var meta = TaskAssignByMemberCreationMeta.builder()
				.assignerId(memberId)
				.assigneeId(assigneeId)
				.taskId(taskId)
				.build();
		
		var assignment = taskAssignService.create(organizationId, meta);
	}

}
