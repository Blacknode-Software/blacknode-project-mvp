package software.blacknode.backend.api.controller.role.mapper.impl;

import java.util.UUID;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.mapper.annotation.PatchOperationsMappingRequest;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.role.mapper.RoleMapper;
import software.blacknode.backend.api.controller.role.request.RolePatchRequest;
import software.blacknode.backend.api.controller.role.response.RolePatchResponse;
import software.blacknode.backend.api.controller.role.response.content.annotation.RoleResponseContentMapping;
import software.blacknode.backend.application.role.command.RolePatchCommand;
import software.blacknode.backend.application.role.usecase.RolePatchUseCase;

@Mapper(componentModel = "spring")
public interface RolePatchMapper extends RoleMapper, ResponseMapper<RolePatchUseCase.Result, RolePatchResponse>{

	@PatchOperationsMappingRequest
	RolePatchCommand toCommand(RolePatchRequest request, UUID id);
	
	@Override
	@RoleResponseContentMapping
	RolePatchResponse toResponse(RolePatchUseCase.Result result);
	
}
