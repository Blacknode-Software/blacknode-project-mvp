package software.blacknode.backend.application.task.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.task.command.TaskDeleteCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;

@RequiredArgsConstructor
public class TaskDeleteUseCase implements ExecutionUseCase<TaskDeleteCommand> {

	private final AccessControlService accessControlService;
	private final SharedDeletionService sharedDeletionService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public void execute(TaskDeleteCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		var taskId = command.getTaskId();
		
		accessControlService.ensureMemberHasTaskAccess(organizationId, memberId, 
				taskId, AccessControlService.AccessLevel.MANAGE);
		
		sharedDeletionService.deleteTaskCascade(organizationId, taskId);
	}

}
