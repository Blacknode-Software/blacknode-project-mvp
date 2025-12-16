package software.blacknode.backend.api.controller.project.converter;

import software.blacknode.backend.api.controller.converter.BaseRequestConverter;
import software.blacknode.backend.api.controller.project.request.ProjectPatchRequest;
import software.blacknode.backend.application.project.command.ProjectPatchCommand;

public class ProjectPatchRequestConvert implements BaseRequestConverter<ProjectPatchRequest, ProjectPatchCommand> {

	@Override
	public ProjectPatchCommand convert(ProjectPatchRequest request) {

		ProjectPatchCommand.builder().
		
		return null;
	}
	
}
