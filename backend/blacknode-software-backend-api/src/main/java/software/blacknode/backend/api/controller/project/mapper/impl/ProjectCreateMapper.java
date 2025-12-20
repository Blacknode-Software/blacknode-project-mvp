package software.blacknode.backend.api.controller.project.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.request.ProjectCreateRequest;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.application.project.command.ProjectCreateCommand;
import software.blacknode.backend.application.project.usecase.ProjectCreateUseCase;

@Mapper(componentModel = "spring")
public interface ProjectCreateMapper extends RequestMapper<ProjectCreateRequest, ProjectCreateCommand>, ResponseMapper<ProjectCreateUseCase.Result, ProjectCreateResponse> {

	@Override
	ProjectCreateCommand toCommand(ProjectCreateRequest request);
	
	@Override
	ProjectCreateResponse toResponse(ProjectCreateUseCase.Result result);
}
