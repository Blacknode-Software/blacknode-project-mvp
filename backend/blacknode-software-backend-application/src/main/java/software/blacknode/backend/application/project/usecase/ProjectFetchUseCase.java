package software.blacknode.backend.application.project.usecase;

import software.blacknode.backend.application.project.command.ProjectFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;

public class ProjectFetchUseCase implements ResultExecutionUseCase<ProjectFetchCommand, ProjectFetchUseCase.Result> {
	
	@Override
	public Result execute(ProjectFetchCommand command) {
		
		return null;
	}

	public static class Result {
		
	}

}
