package software.blacknode.backend.api.controller.project.converter;

import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseRequestConverter;
import software.blacknode.backend.api.controller.project.request.ProjectCreateRequest;
import software.blacknode.backend.application.project.command.ProjectCreateCommand;

@Component
public class ProjectCreateRequestConverter implements BaseRequestConverter<ProjectCreateRequest, ProjectCreateCommand> {

	@Override
	public ProjectCreateCommand convert(ProjectCreateRequest request) {
		var name = request.getName();
		var description = request.getDescription();
		var color = request.getColor();
		
		return ProjectCreateCommand.builder()
				.name(name)
				.description(description)
				.color(color)
				.build();
	}
}
