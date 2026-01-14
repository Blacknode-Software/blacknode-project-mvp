package software.blacknode.backend.application.profile.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.account.AccountService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.profile.command.ProfilesBatchFetchCommand;
import software.blacknode.backend.application.profile.dto.ProfileDTO;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ProfilesBatchFetchUseCase implements ResultExecutionUseCase<ProfilesBatchFetchCommand, ProfilesBatchFetchUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final MemberService memberService;
	
	private final AccountService accountService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ProfilesBatchFetchCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(
				organizationId, memberId, AccessLevel.READ);
		
		var memberIds = command.getMemberIds();
		
		var members = memberService.getByIds(organizationId, memberIds);
		
		var accountIds = members.stream()
				.map(member -> member.getAccountId())
				.toList();
		
		var accountsMap = accountService.getByIds(accountIds).stream()
			.collect(Collectors.toMap(account -> account.getId(), account -> account));
		
		var profiles = members.stream().map(member -> {
			var account = accountsMap.get(member.getAccountId());
			
			var firstName = account.getMeta().getFirstName();
			var lastName = account.getMeta().getLastName();
			
			var displayName = String.format("%s %s", firstName, lastName).trim();
			var email = account.getEmail();
			
			var profile = ProfileDTO.builder()
					.memberId(member.getId())
					.displayName(displayName)
					.email(email)
					.build();
			
			return profile;
		}).collect(Collectors.toList());
		
		return Result.builder()
				.profiles(profiles)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {

		@NonNull
		private final List<ProfileDTO> profiles;
		
	}

}
