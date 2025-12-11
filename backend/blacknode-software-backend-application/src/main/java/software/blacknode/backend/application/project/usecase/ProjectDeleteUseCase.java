package software.blacknode.backend.application.project.usecase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectDeleteCommand;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.project.meta.delete.ProjectDefaultDeletionMeta;

@Service
@RequiredArgsConstructor
public class ProjectDeleteUseCase implements ExecutionUseCase<ProjectDeleteCommand> {

	private final ProjectService projectService;
	
	@Override
	public void execute(ProjectDeleteCommand command) {
		var projectId = command.getProjectId();
		
		var meta = ProjectDefaultDeletionMeta.builder()
				.build();
		
		projectService.delete(projectId, meta);		
	}

}
