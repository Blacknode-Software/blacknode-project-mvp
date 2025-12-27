package software.blacknode.backend.api.controller.project.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.annotation.PatchOperationsMappingRequest;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.request.ProjectPatchRequest;
import software.blacknode.backend.api.controller.project.response.ProjectPatchResponse;
import software.blacknode.backend.api.controller.project.response.content.annotation.ProjectResponseContentMapping;
import software.blacknode.backend.application.project.command.ProjectPatchCommand;
import software.blacknode.backend.application.project.usecase.ProjectPatchUseCase;

@Mapper(componentModel = "spring")
public interface ProjectPatchMapper extends ResponseMapper<ProjectPatchUseCase.Result, ProjectPatchResponse> {

	@PatchOperationsMappingRequest
	ProjectPatchCommand toCommand(ProjectPatchRequest request, UUID id);
	
	@ProjectResponseContentMapping
	ProjectPatchResponse toResponse(ProjectPatchUseCase.Result result);
	
}
