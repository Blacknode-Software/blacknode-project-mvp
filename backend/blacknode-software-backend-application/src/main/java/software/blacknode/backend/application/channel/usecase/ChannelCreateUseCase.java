package software.blacknode.backend.application.channel.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelCreateCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.meta.create.impl.ChannelDefaultCreationMeta;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ChannelCreateUseCase implements ResultExecutionUseCase<ChannelCreateCommand, ChannelCreateUseCase.Result> {

	private final AccessControlService accessControlService;
	
	private final ChannelService channelService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public Result execute(ChannelCreateCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		var projectId = command.getProjectId();
		
		
		accessControlService.ensureMemberHasProjectAccess(organizationId, memberId, 
				projectId, AccessLevel.MANAGE);

		var name = command.getName();
		var description = command.getDescription();
		var color = command.getColor();
		
		var meta = ChannelDefaultCreationMeta.builder()
				.name(name)
				.description(description)
				.color(color)
				.projectId(projectId)
				.build();
		
		var channel = channelService.create(organizationId, meta);
	
		return Result.builder()
				.channelId(channel.getId())
				.build();
	}

	@Getter
	@Builder
	@NonNull
	public static class Result {
		
		private final HUID channelId;
		
	}
}
