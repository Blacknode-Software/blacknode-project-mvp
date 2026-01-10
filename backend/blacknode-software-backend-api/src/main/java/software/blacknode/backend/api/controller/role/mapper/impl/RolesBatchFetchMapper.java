package software.blacknode.backend.api.controller.role.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.api.controller.role.mapper.RoleMapper;
import software.blacknode.backend.api.controller.role.request.RolesBatchFetchRequest;
import software.blacknode.backend.api.controller.role.response.RolesBatchFetchResponse;
import software.blacknode.backend.application.role.command.RolesBatchFetchCommand;
import software.blacknode.backend.application.role.usecase.RolesBatchFetchUseCase;

@Mapper(componentModel = "spring")
public interface RolesBatchFetchMapper extends RoleMapper, RequestMapper<RolesBatchFetchRequest, RolesBatchFetchCommand>, ResponseMapper<RolesBatchFetchUseCase.Result, RolesBatchFetchResponse> {

	@Override
	@Mapping(target = "roleIds", source = "ids")
	RolesBatchFetchCommand toCommand(RolesBatchFetchRequest request);
	
	@Override
	@Mapping(target = "items", source = "roles")
	RolesBatchFetchResponse toResponse(RolesBatchFetchUseCase.Result result);
	
}
