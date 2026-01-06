package software.blacknode.backend.api.controller.account.mapper.impl;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.account.mapper.AccountMapper;
import software.blacknode.backend.api.controller.account.response.AccountResponse;
import software.blacknode.backend.api.controller.account.response.content.annotation.AccountResponseContentMapping;
import software.blacknode.backend.api.controller.mapper.impl.ResponseMapper;
import software.blacknode.backend.application.account.usecase.AccountFetchUseCase;

@Mapper(componentModel = "spring")
public interface AccountFetchMapper extends AccountMapper, ResponseMapper<AccountFetchUseCase.Result, AccountResponse> {

	@Override
	@AccountResponseContentMapping
	AccountResponse toResponse(AccountFetchUseCase.Result result);
	
}
