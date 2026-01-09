package software.blacknode.backend.api.controller.role.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.role.mapper.RoleMapper;
import software.blacknode.backend.api.controller.role.response.RoleResponse;
import software.blacknode.backend.api.controller.role.response.content.annotation.RoleResponseContentMapping;
import software.blacknode.backend.application.role.usecase.RoleFetchUseCase;

@Mapper(componentModel = "spring")
public interface RoleFetchMapper extends RoleMapper, ResponseMapper<RoleFetchUseCase.Result, RoleResponse> {

	@Override
	@RoleResponseContentMapping
	RoleResponse toResponse(RoleFetchUseCase.Result result);
	
}

