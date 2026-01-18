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
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.command.MembersInChannelCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MembersInChannelUseCase implements ResultExecutionUseCase<MembersInChannelCommand, MembersInChannelUseCase.Result> {

	private final ChannelAccessControl channelAccessControl;
	
	private final MemberService memberService;
	
	private final ChannelService channelService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(MembersInChannelCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
	
		var channelId = command.getChannelId();
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		channelAccessControl.ensureMemberHasChannelAccess(memberId, channel, AccessLevel.READ);
		
		var members = memberService.getAll(organizationId)
				.stream()
				.filter(m -> channelAccessControl.hasAccessToChannel(
						m, channel, AccessLevel.READ))
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
