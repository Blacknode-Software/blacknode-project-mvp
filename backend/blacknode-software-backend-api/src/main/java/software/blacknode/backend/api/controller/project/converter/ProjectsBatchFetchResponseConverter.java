package software.blacknode.backend.api.controller.project.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;
import software.blacknode.backend.api.controller.project.response.ProjectsBatchResponse;
import software.blacknode.backend.application.project.usecase.ProjectsBatchFetchUseCase;

@Component
public class ProjectsBatchFetchResponseConverter implements BaseResponseConverter<ProjectsBatchFetchUseCase.Result, ProjectsBatchResponse> {

	@Override
	public ProjectsBatchResponse convert(ProjectsBatchFetchUseCase.Result result) {
		var projects = result.getProjects();
		
		var response = ProjectsBatchResponse.builder()
				.items(projects.stream()
						.collect(Collectors.toMap(p -> p.getId().toUUID(), ProjectResponse::from)))
				.build();
		
		return response;
	}


}
