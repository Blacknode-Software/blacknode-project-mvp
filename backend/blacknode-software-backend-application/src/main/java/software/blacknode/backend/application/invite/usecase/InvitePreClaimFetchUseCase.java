package software.blacknode.backend.application.invite.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.invite.InviteService;
import software.blacknode.backend.application.invite.command.InvitePreClaimFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Service
@RequiredArgsConstructor
public class InvitePreClaimFetchUseCase implements ResultExecutionUseCase<InvitePreClaimFetchCommand, InvitePreClaimFetchUseCase.Result> {

	private final InviteService inviteService;
	
	@Override
	@Transactional
	public Result execute(InvitePreClaimFetchCommand command) {
		var token = command.getToken();
		
		var invite = inviteService.getByToken(token).orElseThrow(() -> 
			new BlacknodeException("Invite with the provided token does not exist.")
		);
		
		var email = invite.getMeta().getEmail();
		
		return Result.builder()
				.email(email)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final String email;
		
	}

}
