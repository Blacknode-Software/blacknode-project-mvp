package software.blacknode.backend.application.auth.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.auth.AuthService;
import software.blacknode.backend.application.auth.command.AuthenticateWithPasswordCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.auth.exception.AuthenticationException;
import software.blacknode.backend.domain.auth.method.impl.PasswordAuthMethod.PasswordAuthMethodMeta;
import software.blacknode.backend.domain.auth.method.type.impl.BaseAuthMethodType;

@Service
@RequiredArgsConstructor
public class AuthenticateWithPasswordUseCase implements ResultExecutionUseCase<AuthenticateWithPasswordCommand, AuthenticateWithPasswordUseCase.Result> {

	private final AccountService accountService;
	
	private final AuthService authService;
	
	@Override
	@Transactional
	public Result execute(AuthenticateWithPasswordCommand command) {
		var email = command.getEmail().trim().toLowerCase();
		var password = command.getPassword().trim();
		
		var account = accountService.getByEmail(email)
				.orElseThrow(() -> new AuthenticationException("No account found for the provided email."));
		
		var accountId = account.getId();
		
		var auth = authService.getByMethodTypeOrThrow(accountId, BaseAuthMethodType.PASSWORD);
		
		var meta = PasswordAuthMethodMeta.builder()
				.password(password)
				.build();
		
		var result = auth.authenticate(meta);
		
		if(!result) throw new AuthenticationException("Account couldn't be authenticated!");
		
		return Result.builder()
				.email(email)
				.accountId(accountId)
				.build();
	}

	@Getter
	@Builder
	public static class Result {
		
		@NonNull private HUID accountId;
		@NonNull private String email;
		
	}

}
