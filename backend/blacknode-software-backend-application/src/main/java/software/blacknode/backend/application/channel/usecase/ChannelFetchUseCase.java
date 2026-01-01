package software.blacknode.backend.application.channel.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;


@Service
@RequiredArgsConstructor
public class ChannelFetchUseCase implements ResultExecutionUseCase<ChannelFetchCommand, ChannelFetchUseCase.Result> {

	private final AccessControlService accessControlService;

	private final ChannelService channelService;

	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ChannelFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		accessControlService.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessLevel.READ);
		
		var channel = channelService.getOrThrow(organizationId, channelId);
	
		return Result.builder()
				.channel(channel)
				.build();
	}

	@Getter
	@Builder
	@NonNull
	public static class Result {

		private final Channel channel;

	}

}
