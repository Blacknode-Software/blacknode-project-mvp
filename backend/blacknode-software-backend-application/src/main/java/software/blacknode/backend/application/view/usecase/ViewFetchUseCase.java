package software.blacknode.backend.application.view.usecase;

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
import software.blacknode.backend.application.view.command.ViewFetchCommand;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;
import software.blacknode.backend.domain.view.View;

@Service
@RequiredArgsConstructor
public class ViewFetchUseCase implements ResultExecutionUseCase<ViewFetchCommand, ViewFetchUseCase.Result> {

	private final ViewService viewService;
	
	private final AccessControlService accessControlService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(ViewFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var viewId = command.getViewId();
		
		accessControlService.ensureMemberHasViewAccess(organizationId, memberId, 
				viewId, AccessLevel.READ);
		
		var view = viewService.getOrThrow(organizationId, viewId);
		
		return Result.builder()
				.view(view)
				.build();
	}

	@Builder
	@Getter
	@ToString
	public static class Result {
		
		@NonNull
		private final View view;
		
	}

}
