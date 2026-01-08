package software.blacknode.backend.application.channel.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.impl.ProjectAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelsInProjectCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ChannelsInProjectUseCase implements ResultExecutionUseCase<ChannelsInProjectCommand, ChannelsInProjectUseCase.Result> {

	private final ProjectAccessControl projectAccessControl;
	private final ChannelAccessControl channelAccessControl;
	
	private final ChannelService channelService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ChannelsInProjectCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var projectId = command.getProjectId();
		
		projectAccessControl.ensureMemberHasProjectAccess(organizationId, memberId, 
				projectId, AccessLevel.READ);
		
		var channels = channelService.getAllInProject(organizationId, projectId);
		
		var channelsIds = channels.stream()
				.filter(channel -> channelAccessControl.hasAccessToChannel(organizationId, memberId, channel.getId(), AccessLevel.READ))
				.map(Channel::getId)
				.toList();
		
		return Result.builder()
				.channelsIds(channelsIds)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private List<HUID> channelsIds;
		
	}

}
