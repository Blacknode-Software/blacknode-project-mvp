package software.blacknode.backend.api.controller.role.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.role.mapper.RoleMapper;
import software.blacknode.backend.api.controller.role.request.RoleCreateRequest;
import software.blacknode.backend.api.controller.role.response.RoleCreateResponse;
import software.blacknode.backend.application.role.command.RoleCreateCommand;
import software.blacknode.backend.application.role.usecase.RoleCreateUseCase;

@Mapper(componentModel = "spring")
public interface RoleCreateMapper extends RoleMapper, RequestMapper<RoleCreateRequest, RoleCreateCommand>, ResponseMapper<RoleCreateUseCase.Result, RoleCreateResponse>{

	@Override
	RoleCreateCommand toCommand(RoleCreateRequest request);
	
	@Override
	RoleCreateResponse toResponse(RoleCreateUseCase.Result result);
	
}
