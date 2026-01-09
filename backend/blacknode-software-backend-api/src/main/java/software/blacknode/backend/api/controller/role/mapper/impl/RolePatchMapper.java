package software.blacknode.backend.api.controller.role.mapper.impl;

import software.blacknode.backend.api.controller.mapper.annotation.PatchOperationsMappingRequest;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.role.request.RolePatchRequest;
import software.blacknode.backend.api.controller.role.response.RolePatchResponse;
import software.blacknode.backend.api.controller.role.response.content.annotation.RoleResponseContentMapping;
import software.blacknode.backend.application.role.command.RolePatchCommand;
import software.blacknode.backend.application.role.usecase.RolePatchUseCase;

public interface RolePatchMapper extends RequestMapper<RolePatchRequest, RolePatchCommand>, ResponseMapper<RolePatchUseCase.Result, RolePatchResponse>{

	@Override
	@PatchOperationsMappingRequest
	RolePatchCommand toCommand(RolePatchRequest request);
	
	@Override
	@RoleResponseContentMapping
	RolePatchResponse toResponse(RolePatchUseCase.Result result);
	
}
