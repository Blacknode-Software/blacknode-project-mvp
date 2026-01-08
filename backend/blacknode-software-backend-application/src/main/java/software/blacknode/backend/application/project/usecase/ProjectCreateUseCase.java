package software.blacknode.backend.application.project.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectCreateCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.project.meta.create.impl.ProjectDefaultCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProjectCreateUseCase implements ResultExecutionUseCase<ProjectCreateCommand, ProjectCreateUseCase.Result> {
	
	private final OrganizationAccessControl organizationAccessControl;
	private final ProjectService projectService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ProjectCreateCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(memberId, organizationId, AccessLevel.MANAGE);
		
		var projectName = command.getName();
		var projectDescription = command.getDescription();
		var projectColor = command.getColor();
		
		var meta = ProjectDefaultCreationMeta.builder()
				.name(projectName)
				.description(projectDescription)
				.color(projectColor)
				.build();
		
		var project = projectService.create(organizationId, meta);
		
		return Result.builder().projectId(project.getId()).build();
	}

	@Getter
	@Builder
	public static class Result {
		
		@NonNull
		private final HUID projectId;
	}

}
