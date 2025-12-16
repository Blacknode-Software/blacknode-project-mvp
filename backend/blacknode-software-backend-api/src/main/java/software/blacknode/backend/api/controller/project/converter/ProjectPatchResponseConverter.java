package software.blacknode.backend.api.controller.project.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import software.blacknode.backend.api.controller.converter.BaseResponseConverter;
import software.blacknode.backend.api.controller.project.response.ProjectPatchResponse;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.application.project.usecase.ProjectPatchUseCase;

@Component
public class ProjectPatchResponseConverter implements BaseResponseConverter<ProjectPatchUseCase.Result, ProjectPatchResponse> {

	@Override
	public ProjectPatchResponse convert(ProjectPatchUseCase.Result result) {
		var project = result.getProject();
		
		var content = ProjectResponseContent.from(project);
		
		var response = ProjectPatchResponse.builder().build();
		
		BeanUtils.copyProperties(content, response);
		
		return response;
	}
}
