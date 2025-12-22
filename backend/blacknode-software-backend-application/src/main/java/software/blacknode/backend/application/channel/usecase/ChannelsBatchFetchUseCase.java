package software.blacknode.backend.application.channel.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelsBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ChannelsBatchFetchUseCase implements ResultExecutionUseCase<ChannelsBatchFetchCommand, ChannelsBatchFetchUseCase.Result> {

	private final AccessControlService accessControlService;
	private final ChannelService channelService;
	
	@Autowired
	private SessionContext context;
	
	@Override
	public Result execute(ChannelsBatchFetchCommand command) {
		var memberId = context.getMemberId();
		var organizationId = context.getOrganizationId();
		
		var channelIds = command.getChannelIds();
		
		var channels = channelService.getByIds(organizationId, channelIds);
		
		channels = channels.stream()
				.filter(channel -> accessControlService.hasAccessToChannel(organizationId, memberId, channel.getId(), AccessControlService.AccessLevel.READ))
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
