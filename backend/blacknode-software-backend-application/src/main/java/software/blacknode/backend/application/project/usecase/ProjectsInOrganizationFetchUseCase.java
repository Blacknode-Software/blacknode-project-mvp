package software.blacknode.backend.application.project.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectsInOrganizationFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ProjectsInOrganizationFetchUseCase implements ResultExecutionUseCase<ProjectsInOrganizationFetchCommand, ProjectsInOrganizationFetchUseCase.Result> {
	
	private final AccessControlService accessControlService;
	private final ProjectService projectService;
	
	@Autowired
	private SessionContext context;
	
	
	@Override
	public Result execute(ProjectsInOrganizationFetchCommand command) {
		var memberId = context.getMemberId();
		var organizationId = context.getOrganizationId();
		
		var projects = projectService.getAll(organizationId);
		
		var projectIds = projects.stream()
				.filter(project -> accessControlService.hasAccessToProject(memberId, project, AccessControlService.AccessLevel.READ))
				.map(project -> project.getId())
				.toList();
		
		return Result.builder().projectsIds(projectIds).build();
	}

	@Getter
	@Builder
	public static class Result {
		@NonNull
		private List<HUID> projectsIds;
	}

}
