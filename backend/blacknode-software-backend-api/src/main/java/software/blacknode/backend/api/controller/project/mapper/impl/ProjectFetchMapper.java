package software.blacknode.backend.api.controller.project.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;
import software.blacknode.backend.api.controller.project.response.content.annotation.ProjectResponseContentMapping;
import software.blacknode.backend.application.project.usecase.ProjectFetchUseCase;
import software.blacknode.backend.application.project.usecase.ProjectFetchUseCase.Result;

@Mapper(componentModel = "spring")
public interface ProjectFetchMapper extends ResponseMapper<ProjectFetchUseCase.Result, ProjectResponse> {

	
	@Override
	@ProjectResponseContentMapping
	ProjectResponse toResponse(Result result);
	
}


