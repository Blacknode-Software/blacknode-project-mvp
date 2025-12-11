package software.blacknode.backend.application.project.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectsInOrganizationFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ProjectsInOrganizationFetchUseCase implements ResultExecutionUseCase<ProjectsInOrganizationFetchCommand, ProjectsInOrganizationFetchUseCase.Result> {
	
	private final MemberAssociationService memberAssociationService;
	private final ProjectService projectService;
	
	@Autowired
	private SessionContext context;
	
	
	@Override
	public Result execute(ProjectsInOrganizationFetchCommand command) {
		var memberId = context.getMemberId();
		
		var projects = memberAssociationService.filterAccessibleProjects(memberId, projectService.getAllInOrganization());
		
		var projectIds = projects.stream()
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
