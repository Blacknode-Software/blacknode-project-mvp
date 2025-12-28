package software.blacknode.backend.application.view.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.application.view.command.ViewsInChannelCommand;
import software.blacknode.backend.domain.context.SessionContext;

@Service
@RequiredArgsConstructor
public class ViewsInChannelUseCase implements ResultExecutionUseCase<ViewsInChannelCommand, ViewsInChannelUseCase.Result> {
	
	private final AccessControlService accessControlService;
	
	private final ViewService viewService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public Result execute(ViewsInChannelCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		var channelId = command.getChannelId();
		
		accessControlService.ensureMemberHasChannelAccess(organizationId, memberId, 
				channelId, AccessLevel.READ);
		
		var views = viewService.getAllInChannel(organizationId, channelId);
		
		var viewIds = views.stream()
				/* CURRENT VERSION: All views are accessible by default in this version */
				.map(v -> v.getId())
				.toList();
		
		return Result.builder()
				.viewIds(viewIds)
				.build();
	}
	
	
	@Getter
	@Builder
	@ToString
	public static class Result {
	
		@NonNull
		private final List<HUID> viewIds;
		
	}
	
}

