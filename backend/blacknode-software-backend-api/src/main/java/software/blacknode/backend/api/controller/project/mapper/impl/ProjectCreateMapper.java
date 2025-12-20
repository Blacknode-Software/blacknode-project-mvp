package software.blacknode.backend.api.controller.project.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.request.ProjectCreateRequest;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.application.project.command.ProjectCreateCommand;
import software.blacknode.backend.application.project.usecase.ProjectCreateUseCase;

@Mapper(componentModel = "spring")
public interface ProjectCreateMapper extends RequestMapper<ProjectCreateRequest, ProjectCreateCommand>, ResponseMapper<ProjectCreateUseCase.Result, ProjectCreateResponse> {

	@Override
	@Mapping(target = "name", source = "name")
	@Mapping(target = "description", source = "description")
	@Mapping(target = "color", source = "color")
	ProjectCreateCommand toCommand(ProjectCreateRequest request);
	
	@Override
	@Mapping(target = "projectId", source = "projectId")
	ProjectCreateResponse toResponse(ProjectCreateUseCase.Result result);
}
