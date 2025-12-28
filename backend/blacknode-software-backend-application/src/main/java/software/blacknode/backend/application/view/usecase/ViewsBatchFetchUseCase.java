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
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.application.view.command.ViewsBatchFetchCommand;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.view.View;

@Service
@RequiredArgsConstructor
public class ViewsBatchFetchUseCase implements ResultExecutionUseCase<ViewsBatchFetchCommand, ViewsBatchFetchUseCase.Result> {
	
	private final AccessControlService accessControlService;
	private final ViewService viewService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public Result execute(ViewsBatchFetchCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var meberId = sessionContext.getMemberId();
		
		var viewIds = command.getViewIds();
		
		var views = viewService.getByIds(organizationId, viewIds)
				.stream()
				.filter(view -> accessControlService.hasAccessToView(meberId, view, 
						AccessLevel.READ))
				.toList();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private List<View> views;
		
	}


}
