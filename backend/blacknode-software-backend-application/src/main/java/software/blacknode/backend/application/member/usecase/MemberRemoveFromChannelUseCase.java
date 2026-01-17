package software.blacknode.backend.application.member.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.command.MemberRemoveFromChannelCommand;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class MemberRemoveFromChannelUseCase implements ExecutionUseCase<MemberRemoveFromChannelCommand> {

	private final ChannelAccessControl channelAccessControl;
	
	private final ChannelService channelService;
	
	private final MemberService memberService;
	
	private final SharedDeletionService sharedDeletionService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(MemberRemoveFromChannelCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		var targetMemberId = command.getMemberId();
		var targetMember = memberService.getOrThrow(organizationId, targetMemberId);
		
		channelAccessControl.ensureMemberHasChannelAccess(memberId, channel, AccessLevel.MANAGE);
		
		channelAccessControl.ensureMemberHasChannelAccess(targetMember, channel, AccessLevel.READ);
		
		sharedDeletionService.deleteMemberFromChannel(organizationId, targetMemberId, channelId);
	}

}
