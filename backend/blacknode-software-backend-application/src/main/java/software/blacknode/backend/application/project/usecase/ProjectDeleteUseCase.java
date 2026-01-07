package software.blacknode.backend.application.project.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.project.command.ProjectDeleteCommand;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProjectDeleteUseCase implements ExecutionUseCase<ProjectDeleteCommand> {

	private final SharedDeletionService sharedDeletionService;
	private final AccessControlService accessControlService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(ProjectDeleteCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getProjectId();
		
		accessControlService.ensureMemberHasOrganizationAccess(memberId, organizationId, AccessLevel.MANAGE);
		
		sharedDeletionService.deleteProjectCascade(organizationId, projectId);
	}

}
