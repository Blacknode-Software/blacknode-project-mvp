package software.blacknode.backend.application.project.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectDeleteCommand;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ProjectDeleteUseCase implements ExecutionUseCase<ProjectDeleteCommand> {

	private final SharedDeletionService sharedDeletionService;
	private final AccessControlService accessControlService;
	
	@Autowired
	private SessionContext context;
	
	@Override
	public void execute(ProjectDeleteCommand command) {
		var memberId = context.getMemberId();
		var organizationId = context.getOrganizationId();
		
		var projectId = command.getProjectId();
		
		accessControlService.ensureMemberHasOrganizationAccess(memberId, organizationId, AccessLevel.MANAGE);
		
		sharedDeletionService.deleteProjectCascade(organizationId, projectId);
	}

}
