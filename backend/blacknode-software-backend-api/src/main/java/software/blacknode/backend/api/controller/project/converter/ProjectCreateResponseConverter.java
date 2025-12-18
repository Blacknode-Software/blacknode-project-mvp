package software.blacknode.backend.api.controller.project.converter;

import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.application.project.usecase.ProjectCreateUseCase;

@Component
public class ProjectCreateResponseConverter implements BaseResponseConverter<ProjectCreateUseCase.Result, ProjectCreateResponse> {

	@Override
	public ProjectCreateResponse convert(ProjectCreateUseCase.Result result) {
		var projectId = result.getProjectId();
		
		return ProjectCreateResponse.builder()
				.projectId(projectId.toUUID())
				.build();
	}

}
