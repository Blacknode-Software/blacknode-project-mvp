package software.blacknode.backend.api.controller.account.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.api.controller.account.response.content.AccountResponseContent;
import software.blacknode.backend.api.controller.account.response.content.annotation.AccountResponseContentMapping;
import software.blacknode.backend.domain.account.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

	@AccountResponseContentMapping
	AccountResponseContent toResponseContent(Account account);
	
}
