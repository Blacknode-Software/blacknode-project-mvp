package software.blacknode.backend.application.channel.usecase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelDeleteCommand;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ChannelDeleteUseCase implements ExecutionUseCase<ChannelDeleteCommand> {

	private final SharedDeletionService sharedDeletionService;
	
	private final AccessControlService accessControlService;	

	private final ChannelService channelService;

	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public void execute(ChannelDeleteCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		var projectId = channel.getProjectId();
		
		accessControlService.ensureMemberHasProjectAccess(organizationId, memberId, 
				projectId, AccessLevel.MANAGE);
		
		sharedDeletionService.deleteChannelCascade(organizationId, channelId);
	}

}
