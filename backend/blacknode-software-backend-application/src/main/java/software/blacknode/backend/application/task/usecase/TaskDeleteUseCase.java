package software.blacknode.backend.application.task.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.task.command.TaskDeleteCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.SessionContext;

@Service
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
				taskId, AccessLevel.MANAGE);
		
		sharedDeletionService.deleteTaskCascade(organizationId, taskId);
	}

}
