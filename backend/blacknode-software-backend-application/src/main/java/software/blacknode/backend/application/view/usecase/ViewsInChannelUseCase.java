package software.blacknode.backend.application.view.usecase;

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
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.application.view.command.ViewsInChannelCommand;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class ViewsInChannelUseCase implements ResultExecutionUseCase<ViewsInChannelCommand, ViewsInChannelUseCase.Result> {
	
	private final ChannelAccessControl channelAccessControl;
	
	private final ViewService viewService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(ViewsInChannelCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var channelId = command.getChannelId();
		
		channelAccessControl.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessLevel.READ);
		
		var views = viewService.getAllInChannel(organizationId, channelId);
		
		var viewIds = views.stream()
				/* CURRENT VERSION: All views are accessible by default in this version */
				.map(v -> v.getId())
				.toList();
		
		return Result.builder()
				.viewsIds(viewIds)
				.build();
	}
	
	
	@Getter
	@Builder
	@ToString
	public static class Result {
	
		@NonNull
		private final List<HUID> viewsIds;
		
	}
	
}

