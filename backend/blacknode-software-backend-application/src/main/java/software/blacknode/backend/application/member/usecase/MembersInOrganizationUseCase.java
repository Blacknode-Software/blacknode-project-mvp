package software.blacknode.backend.application.member.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.command.MembersInOrganizationCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MembersInOrganizationUseCase implements ResultExecutionUseCase<MembersInOrganizationCommand, MembersInOrganizationUseCase.Result> {

	private final AccessControlService accessControlService;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(MembersInOrganizationCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var members = memberService.getAll(organizationId)
				.stream()
				.map(member -> member.getId())
				.toList();
		
		return Result.builder()
				.memberIds(members)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<HUID> memberIds;
		
	}

}
