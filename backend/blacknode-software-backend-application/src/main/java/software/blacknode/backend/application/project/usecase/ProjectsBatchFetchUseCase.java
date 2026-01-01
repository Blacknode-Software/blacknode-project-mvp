package software.blacknode.backend.application.project.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProjectsBatchFetchUseCase implements ResultExecutionUseCase<ProjectsBatchFetchCommand, ProjectsBatchFetchUseCase.Result> {
	
	private final AccessControlService accessControlService;
	private final ProjectService projectService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ProjectsBatchFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectIds = command.getProjectIds();
		
		var projects = projectService.getByIds(organizationId, projectIds)
				.stream()
				.filter(project -> accessControlService.hasAccessToProject(memberId, project, AccessLevel.READ))
				.toList();
		
		return Result.builder().projects(projects).build();
	}

	@Getter
	@Builder
	public static class Result {
		
		@NonNull
		private List<Project> projects;
		
	}

}
