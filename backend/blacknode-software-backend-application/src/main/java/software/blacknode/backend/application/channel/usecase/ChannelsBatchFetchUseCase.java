package software.blacknode.backend.application.channel.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ChannelsBatchFetchUseCase implements ResultExecutionUseCase<ChannelsBatchFetchCommand, ChannelsBatchFetchUseCase.Result> {

	private final AccessControlService accessControlService;
	private final ChannelService channelService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ChannelsBatchFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelIds = command.getChannelIds();
		
		var channels = channelService.getByIds(organizationId, channelIds)
				.stream()
				.filter(channel -> accessControlService.hasAccessToChannel(memberId, channel, 
						AccessLevel.READ))
				.toList();
		
		return Result.builder()
				.channels(channels)
				.build();
	}

	@Getter
	@Builder
	public static class Result {
		
		@NonNull
		private List<Channel> channels;
		
	}

	
	
}
