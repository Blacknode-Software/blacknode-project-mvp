package software.blacknode.backend.api.controller.project.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.application.project.usecase.ProjectFetchUseCase;

@Component
public class ProjectResponseConverter implements BaseResponseConverter<ProjectFetchUseCase.Result, ProjectResponse> {

	@Override
	public ProjectResponse convert(ProjectFetchUseCase.Result result) {
		var project = result.getProject();
		
		var content = ProjectResponseContent.from(project);
		
		var response = ProjectResponse.builder().build();
		
		BeanUtils.copyProperties(content, response);
		
		return response;
	}
}
