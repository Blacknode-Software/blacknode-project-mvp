package software.blacknode.backend.application.account.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.account.command.AccountFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;


@Service
@RequiredArgsConstructor
public class AccountFetchUseCase implements ResultExecutionUseCase<AccountFetchCommand, AccountFetchUseCase.Result> {
	
	private final SessionContextHolder sessionContextHolder;
	private final AccountService accountService;
	
	@Override
	@Transactional
	public Result execute(AccountFetchCommand command) {
		var accountId = sessionContextHolder.getAccountIdOrThrow();
		
		var account = accountService.getOrThrow(accountId);
		
		return Result.builder()
				.account(account)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final Account account;
		
	}


}
