package software.blacknode.backend.application.channel.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ChannelsBatchFetchUseCase implements ResultExecutionUseCase<ChannelsBatchFetchCommand, ChannelsBatchFetchUseCase.Result> {

	private final ChannelAccessControl channelAccessControl;
	private final ChannelService channelService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ChannelsBatchFetchCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelIds = command.getChannelIds();
		
		var channels = channelService.getByIds(organizationId, channelIds)
				.stream()
				.filter(channel -> channelAccessControl.hasAccessToChannel(memberId, channel, 
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
