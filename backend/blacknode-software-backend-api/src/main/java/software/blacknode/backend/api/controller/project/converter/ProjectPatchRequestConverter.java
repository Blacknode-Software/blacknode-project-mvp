package software.blacknode.backend.api.controller.project.converter;

import software.blacknode.backend.api.controller.converter.BaseRequestConverter;
import software.blacknode.backend.api.controller.project.request.ProjectPatchRequest;
import software.blacknode.backend.application.project.command.ProjectPatchCommand;

public class ProjectPatchRequestConverter implements BaseRequestConverter<ProjectPatchRequest, ProjectPatchCommand> {

	@Override
	public ProjectPatchCommand convert(ProjectPatchRequest request) {
		var name = request.getName();
		var description = request.getDescription();
		var color = request.getColor();
		
		var operations = request.getOperations();
		
		return ProjectPatchCommand.builder()
			.name(name)
			.description(description)
			.color(color)
			.operations(operations)
			.build();
	}
	
}
