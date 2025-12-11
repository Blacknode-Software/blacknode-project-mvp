package software.blacknode.backend.application.project.usecase;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.project.Project;

@Service
@RequiredArgsConstructor
public class ProjectFetchUseCase implements ResultExecutionUseCase<ProjectFetchCommand, ProjectFetchUseCase.Result> {
	
	private final ProjectService projectService;
	
	@Override
	public Result execute(ProjectFetchCommand command) {
		// TODO access control
		
		var projectId = command.getProjectId();
		
		var project = projectService.getOrThrow(projectId);
		
		return Result.builder().project(project).build();
	}

	@Getter
	@Builder
	public static class Result {
		
		@NonNull
		private Project project;
	}

}
