package software.blacknode.backend.application.project.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;
import software.blacknode.backend.application.project.ProjectService;
import software.blacknode.backend.application.project.command.ProjectPatchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.modify.impl.ProjectColorModificationMeta;
import software.blacknode.backend.domain.project.meta.modify.impl.ProjectDescriptionModificationMeta;
import software.blacknode.backend.domain.project.meta.modify.impl.ProjectNameModificationMeta;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProjectPatchUseCase implements ResultExecutionUseCase<ProjectPatchCommand, ProjectPatchUseCase.Result> {

	private final AccessControlService accessControlService;
	private final ProjectService projectService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ProjectPatchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getId();
		
		accessControlService.ensureMemberHasProjectAccess(memberId, projectId, organizationId, AccessLevel.MANAGE);
		
		var operations = command.getOperations();
		
		var modifications = ModificationMeta.emptyList();
		
		if(ProjectPatchOperation.DESCRIPTION.isIn(operations)) {
			var name = command.getName();
			
			//TODO add name uniqueness check
			
			var meta = ProjectNameModificationMeta.builder()
					.name(name)
					.build();
			
			modifications.add(meta);
		}
		
		if(ProjectPatchOperation.NAME.isIn(operations)) {
			var description = command.getDescription();
			
			var meta = ProjectDescriptionModificationMeta.builder()
					.description(description)
					.build();
			
			modifications.add(meta);
		}
		
		if(ProjectPatchOperation.COLOR.isIn(operations)) {
			var color = command.getColor();
			
			var meta = ProjectColorModificationMeta.builder()
					.color(color)
					.build();
			
			modifications.add(meta);
		}
		
		var project = projectService.modify(organizationId, projectId, modifications);
		
		return Result.builder().project(project).build();
	}
	
	@Getter
	@Builder
	public static class Result {
		private final Project project;
	}
	
	public static enum ProjectPatchOperation implements PatchOperationEnum {
		NAME,
		DESCRIPTION,
		COLOR
		;
	}

}
