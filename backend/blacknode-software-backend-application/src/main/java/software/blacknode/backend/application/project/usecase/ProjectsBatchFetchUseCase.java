package software.blacknode.backend.application.project.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.project.Project;

@Service
@RequiredArgsConstructor
public class ProjectsBatchFetchUseCase implements ResultExecutionUseCase<ProjectsBatchFetchCommand, ProjectsBatchFetchUseCase.Result> {
	
	private final ProjectService projectService;
	private final MemberAssociationService memberAssociationService;
	
	@Autowired
	private SessionContext context;
	
	@Override
	public Result execute(ProjectsBatchFetchCommand command) {
		var memberId = context.getMemberId();
		
		var projectIds = command.getProjectIds();
		
		if(memberAssociationService.isMemberHavingSuperRoleInOrganization(memberId, context.getOrganizationId()) == false) {
			projectIds = memberAssociationService.filterAccessibleProjectIds(memberId, projectIds);
		}
		
		var projects = projectService.getByIds(projectIds);
				
		return Result.builder().projects(projects).build();
	}

	@Getter
	@Builder
	public static class Result {
		@NonNull
		private List<Project> projects;
		
	}

}
