package software.blacknode.backend.api.controller.project.converter;

import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;
import software.blacknode.backend.api.controller.project.response.ProjectsBatchResponse;
import software.blacknode.backend.application.project.usecase.ProjectsBatchFetchUseCase;
import software.blacknode.backend.domain.project.Project;

@Component
public class ProjectsBatchResponseConverter implements BaseResponseConverter<ProjectsBatchFetchUseCase.Result, ProjectsBatchResponse> {

	@Override
	public ProjectsBatchResponse convert(ProjectsBatchFetchUseCase.Result result) {
		var projects = result.getProjects();
		
		var response = ProjectsBatchResponse.builder()
				.items(projects.stream()
						.map(this::convertProject)
						.toList())
				.build();
		
		return response;
	}


}
