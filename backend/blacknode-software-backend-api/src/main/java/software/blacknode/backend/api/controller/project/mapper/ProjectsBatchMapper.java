package software.blacknode.backend.api.controller.project.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.request.ProjectsBatchFetchRequest;
import software.blacknode.backend.api.controller.project.response.ProjectsBatchFetchResponse;
import software.blacknode.backend.application.project.command.ProjectsBatchFetchCommand;
import software.blacknode.backend.application.project.usecase.ProjectsBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface ProjectsBatchMapper extends RequestMapper<ProjectsBatchFetchRequest, ProjectsBatchFetchCommand>, ResponseMapper<ProjectsBatchFetchUseCase.Result, ProjectsBatchFetchResponse> {

	@Override
	ProjectsBatchFetchCommand toCommand(ProjectsBatchFetchRequest request);
	
	@Override
	ProjectsBatchFetchResponse toResponse(ProjectsBatchFetchUseCase.Result result);
	
}
