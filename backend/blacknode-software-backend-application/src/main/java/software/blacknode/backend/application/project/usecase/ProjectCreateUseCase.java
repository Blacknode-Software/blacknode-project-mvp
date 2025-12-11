package software.blacknode.backend.application.project.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectCreateCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.project.meta.create.ProjectDefaultCreationMeta;

@Service
@RequiredArgsConstructor
public class ProjectCreateUseCase implements ResultExecutionUseCase<ProjectCreateCommand, ProjectCreateUseCase.Result> {
	
	private final MemberAssociationService memberAssociationService;
	private final ProjectService projectService;
	
	@Autowired
	private SessionContext context;
	
	@Override
	public Result execute(ProjectCreateCommand command) {
		var memberId = context.getMemberId();
		var organizationId = context.getOrganizationId();
		
		memberAssociationService.ensureMemberHavingSuperRoleInOrganization(memberId, organizationId);
		
		var projectName = command.getName();
		var projectDescription = command.getDescription();
		var projectColor = command.getColor();
		
		var meta = ProjectDefaultCreationMeta.builder()
				.name(projectName)
				.description(projectDescription)
				.color(projectColor)
				.build();
		
		projectService.create(meta);
		
		// TODO maybe assign the member to the project
		
		return null;
	}

	public static class Result {
		
	}

}
