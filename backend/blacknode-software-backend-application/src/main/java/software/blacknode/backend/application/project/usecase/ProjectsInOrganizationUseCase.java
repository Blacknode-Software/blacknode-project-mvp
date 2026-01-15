package software.blacknode.backend.application.project.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectsInOrganizationCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProjectsInOrganizationUseCase implements ResultExecutionUseCase<ProjectsInOrganizationCommand, ProjectsInOrganizationUseCase.Result> {
	
	private final ProjectAccessControl projectAccessControl;
	private final ProjectService projectService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ProjectsInOrganizationCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projects = projectService.getAll(organizationId);
		
		var projectIds = projects.stream()
				.filter(project -> projectAccessControl.hasAccessToProject(memberId, project, AccessLevel.READ))
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
