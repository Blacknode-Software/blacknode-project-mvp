package software.blacknode.backend.application.channel.usecase;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.ChannelAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.channel.command.ChannelPatchCommand;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.meta.modify.impl.ChannelColorModificationMeta;
import software.blacknode.backend.domain.channel.meta.modify.impl.ChannelDescriptionModificationMeta;
import software.blacknode.backend.domain.channel.meta.modify.impl.ChannelNameModificationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ChannelPatchUseCase implements ResultExecutionUseCase<ChannelPatchCommand, ChannelPatchUseCase.Result> {

	private final ChannelAccessControl channelAccessControl;

	private final ChannelService channelService;

	private final SessionContextHolder sessionContextHolder;

	@Override
	@Transactional
	public Result execute(ChannelPatchCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getId();
		
		channelAccessControl.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessLevel.MANAGE);
		
		var operations = command.getOperations();
		
		var modifications = ModificationMeta.emptyList();
		
		if(ChannelPatchOperation.DESCRIPTION.isIn(operations)) {
			var name = command.getName();
			
			var meta = ChannelNameModificationMeta.builder()
					.name(name)
					.build();
			
			modifications.add(meta);
		}
		
		if(ChannelPatchOperation.DESCRIPTION.isIn(operations)) {
			var description = command.getDescription();
			
			var meta = ChannelDescriptionModificationMeta.builder()
					.description(description)
					.build();
			
			modifications.add(meta);
		}
		
		if(ChannelPatchOperation.COLOR.isIn(operations)) {
			var color = command.getColor();
			
			var meta = ChannelColorModificationMeta.builder()
					.color(color)
					.build();
			
			modifications.add(meta);
		}
		
		var channel = channelService.modify(organizationId, channelId, modifications);
		
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
	
	public static enum ChannelPatchOperation implements PatchOperationEnum {
		NAME,
		DESCRIPTION,
		COLOR,
	}

}
