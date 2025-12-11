package software.blacknode.backend.application.project.usecase;

import software.blacknode.backend.application.project.command.ProjectsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;

public class ProjectsBatchFetchUseCase implements ResultExecutionUseCase<ProjectsBatchFetchCommand, ProjectsBatchFetchUseCase.Result> {
	
	@Override
	public Result execute(ProjectsBatchFetchCommand command) {
		
		return null;
	}

	public static class Result {
		
	}

}
