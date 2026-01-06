package software.blacknode.backend.application.account.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.account.command.AccountPatchCommand;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.meta.modify.impl.AccountFirstNameModificationMeta;
import software.blacknode.backend.domain.account.meta.modify.impl.AccountLastNameModificationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class AccountPatchUseCase implements ResultExecutionUseCase<AccountPatchCommand, AccountPatchUseCase.Result> {

	private final SessionContextHolder sessionContextHolder;
	
	private final AccountService accountService;
	
	@Override
	@Transactional
	public Result execute(AccountPatchCommand command) {
		var accountId = sessionContextHolder.getAccountIdOrThrow();
	
		var operations = command.getOperations();
		
		var modifications = ModificationMeta.emptyList();
		
		if(AccountPatchOperation.FIRST_NAME.isIn(operations)) {
			var firstName = command.getFirstName();
			
			var meta = AccountFirstNameModificationMeta.builder()
					.firstName(firstName)
					.build();
			
			modifications.add(meta);
		}
		
		if(AccountPatchOperation.LAST_NAME.isIn(operations)) {
			var lastName = command.getLastName();
			
			var meta = AccountLastNameModificationMeta.builder()
					.lastName(lastName)
					.build();
			
			modifications.add(meta);
		}
		
		var account = accountService.modify(accountId, modifications);
		
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
	
	public static enum AccountPatchOperation implements PatchOperationEnum {
		FIRST_NAME,
		LAST_NAME,
		;
	}

}
