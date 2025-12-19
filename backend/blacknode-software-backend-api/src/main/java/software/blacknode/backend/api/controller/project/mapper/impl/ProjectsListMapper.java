package software.blacknode.backend.api.controller.project.mapper.impl;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.project.response.ProjectsListResponse;

@Mapper(componentModel = "spring")
public interface ProjectsListMapper extends ResponseMapper<List<HUID>, ProjectsListResponse> {

	@Override
	@Mapping(target = "projectIds", source = ".", qualifiedByName = "huids2UUIDs")
	ProjectsListResponse toResponse(List<HUID> domainEntity);
	
}
