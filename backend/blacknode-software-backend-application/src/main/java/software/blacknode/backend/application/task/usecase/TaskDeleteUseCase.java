package software.blacknode.backend.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.TaskAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.task.command.TaskDeleteCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class TaskDeleteUseCase implements ExecutionUseCase<TaskDeleteCommand> {

	private final TaskAccessControl taskAccessControl;
	private final SharedDeletionService sharedDeletionService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(TaskDeleteCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var taskId = command.getTaskId();
		
		taskAccessControl.ensureMemberHasTaskAccess(organizationId, memberId, 
				taskId, AccessLevel.MANAGE);
		
		sharedDeletionService.deleteTaskCascade(organizationId, taskId);
	}

}
