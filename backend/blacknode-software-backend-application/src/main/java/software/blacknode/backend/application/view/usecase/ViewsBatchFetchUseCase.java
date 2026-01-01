package software.blacknode.backend.application.view.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.application.view.command.ViewsBatchFetchCommand;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.view.View;

@Service
@RequiredArgsConstructor
public class ViewsBatchFetchUseCase implements ResultExecutionUseCase<ViewsBatchFetchCommand, ViewsBatchFetchUseCase.Result> {
	
	private final AccessControlService accessControlService;
	private final ViewService viewService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ViewsBatchFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var viewIds = command.getViewIds();
		
		var views = viewService.getByIds(organizationId, viewIds)
				.stream()
				.filter(view -> accessControlService.hasAccessToView(memberId, view, 
						AccessLevel.READ))
				.toList();
		
		return Result.builder()
				.views(views)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private List<View> views;
		
	}


}
