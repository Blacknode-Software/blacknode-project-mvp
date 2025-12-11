package software.blacknode.backend.application.project.usecase;

import software.blacknode.backend.application.project.command.ProjectsInOrganizationFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;

public class ProjectsInOrganizationFetchUseCase implements ResultExecutionUseCase<ProjectsInOrganizationFetchCommand, ProjectsInOrganizationFetchUseCase.Result> {
	
	@Override
	public Result execute(ProjectsInOrganizationFetchCommand command) {
		
		return null;
	}

	public static class Result {
		
	}

}
