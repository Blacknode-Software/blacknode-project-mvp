package software.blacknode.backend.api.controller.project.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.response.ProjectsListResponse;
import software.blacknode.backend.application.project.usecase.ProjectsInOrganizationUseCase;

@Mapper(componentModel = "spring")
public interface ProjectsInOrganizationFetchMapper extends ResponseMapper<ProjectsInOrganizationUseCase.Result, ProjectsListResponse> {

	@Override
	@Mapping(target = "ids", source = "projectsIds")
	ProjectsListResponse toResponse(ProjectsInOrganizationUseCase.Result result);
	
}
