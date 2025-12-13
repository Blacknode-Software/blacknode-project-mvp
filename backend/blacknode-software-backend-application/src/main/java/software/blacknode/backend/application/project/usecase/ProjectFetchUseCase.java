package software.blacknode.backend.application.project.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.project.Project;

@Service
@RequiredArgsConstructor
public class ProjectFetchUseCase implements ResultExecutionUseCase<ProjectFetchCommand, ProjectFetchUseCase.Result> {
	
	private final ProjectService projectService;
	private final AccessControlService accessControlService;
	
	@Autowired
	private SessionContext context;
	
	@Override
	public Result execute(ProjectFetchCommand command) {
		var organizationId = context.getOrganizationId();
		var memberId = context.getMemberId();
		
		var projectId = command.getProjectId();
		
		var project = projectService.getOrThrow(organizationId, projectId);

		accessControlService.ensureMemberHasProjectAccess(memberId, project, AccessLevel.READ);
		
		return Result.builder().project(project).build();
	}

	@Getter
	@Builder
	public static class Result {
		
		@NonNull
		private Project project;
	}

}
