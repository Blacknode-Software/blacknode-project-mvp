package software.blacknode.backend.api.controller.project.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.mapper.ProjectMapper;
import software.blacknode.backend.api.controller.project.request.ProjectsBatchFetchRequest;
import software.blacknode.backend.api.controller.project.response.ProjectsBatchFetchResponse;
import software.blacknode.backend.application.project.command.ProjectsBatchFetchCommand;
import software.blacknode.backend.application.project.usecase.ProjectsBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface ProjectsBatchFetchMapper extends ProjectMapper, RequestMapper<ProjectsBatchFetchRequest, ProjectsBatchFetchCommand>, ResponseMapper<ProjectsBatchFetchUseCase.Result, ProjectsBatchFetchResponse> {
	
	@Override
	@Mapping(target = "projectIds", source = "ids")
	public abstract ProjectsBatchFetchCommand toCommand(ProjectsBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "projects")
	public ProjectsBatchFetchResponse toResponse(ProjectsBatchFetchUseCase.Result result);
	
}
