package software.blacknode.backend.application.channel.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelsInProjectCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ChannelsInProjectUseCase implements ResultExecutionUseCase<ChannelsInProjectCommand, ChannelsInProjectUseCase.Result> {

	private final AccessControlService accessControlService;
	private final ChannelService channelService;
	
	@Autowired
	private final SessionContext sessionContext;
	
	@Override
	public Result execute(ChannelsInProjectCommand command) {
		
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		var projectId = command.getProjectId();
		
		var channels = channelService.getAllInProject(organizationId, projectId);
		
		var channelsIds = channels.stream()
				.filter(channel -> accessControlService.hasAccessToChannel(organizationId, memberId, channel.getId(), AccessLevel.READ))
				.map(Channel::getId)
				.toList();
		
		return Result.builder()
				.channelsIds(channelsIds)
				.build();
	}

	@Getter
	@Builder
	public static class Result {
		
		private List<HUID> channelsIds;
		
	}

}
