package software.blacknode.backend.api.controller.account.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.account.mapper.AccountMapper;
import software.blacknode.backend.api.controller.account.request.AccountPatchRequest;
import software.blacknode.backend.api.controller.account.response.AccountPatchResponse;
import software.blacknode.backend.api.controller.account.response.content.annotation.AccountResponseContentMapping;
import software.blacknode.backend.api.controller.mapper.annotation.PatchOperationsMappingRequest;
import software.blacknode.backend.api.controller.mapper.impl.RequestMapper;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.account.command.AccountPatchCommand;
import software.blacknode.backend.application.account.usecase.AccountPatchUseCase;

@Mapper(componentModel = "spring")
public interface AccountPatchMapper extends AccountMapper, RequestMapper<AccountPatchRequest, AccountPatchCommand>, ResponseMapper<AccountPatchUseCase.Result, AccountPatchResponse> {

	@Override
	@PatchOperationsMappingRequest
	AccountPatchCommand toCommand(AccountPatchRequest request);

	@Override
	@AccountResponseContentMapping
	AccountPatchResponse toResponse(AccountPatchUseCase.Result result);

}
