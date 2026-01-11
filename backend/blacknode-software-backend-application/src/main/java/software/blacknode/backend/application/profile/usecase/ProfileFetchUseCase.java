package software.blacknode.backend.application.profile.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.profile.command.ProfileFetchCommand;
import software.blacknode.backend.application.profile.dto.ProfileDTO;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProfileFetchUseCase implements ResultExecutionUseCase<ProfileFetchCommand, ProfileFetchUseCase.Result> {
	
	private final OrganizationAccessControl organizationAccessControl;
	
	private final MemberService memberService;
	
	private final AccountService accountService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ProfileFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.READ);
		
		var targetMemberId = command.getMemberId();
		
		var targetMember = memberService.getOrThrow(organizationId, targetMemberId);
		
		var targetAccount = accountService.getOrThrow(targetMember.getAccountId());
		
		var firstName = targetAccount.getMeta().getFirstName();
		var lastName = targetAccount.getMeta().getLastName();
		
		var displayName = String.format("%s %s", firstName, lastName).trim();
		var email = targetAccount.getEmail();
		
		var profile = ProfileDTO.builder()
				.displayName(displayName)
				.email(email)
				.build();
		
		return Result.builder()
				.profile(profile)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final ProfileDTO profile;
		
	}

}
