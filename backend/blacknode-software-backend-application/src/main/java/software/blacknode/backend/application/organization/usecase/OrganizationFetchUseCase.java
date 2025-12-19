package software.blacknode.backend.application.organization.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.organization.command.OrganizationFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.context.SessionContext;
import software.blacknode.backend.domain.organization.Organization;

@RequiredArgsConstructor
public class OrganizationFetchUseCase implements ResultExecutionUseCase<OrganizationFetchCommand, OrganizationFetchUseCase.Result>{

	private final OrganizationService organizationService;
	
	private final AccessControlService accessControlService;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Override
	public Result execute(OrganizationFetchCommand command) {
		var organizationId = sessionContext.getOrganizationId();
		var memberId = sessionContext.getMemberId();
		
		accessControlService.ensureMemberHasOrganizationAccess(memberId, organizationId, AccessLevel.READ);
		
		var organization = organizationService.getOrThrow(organizationId);
		
		return Result.builder()
				.organization(organization)
				.build();
	}
	
	@Getter
	@Builder
	public static class Result {
		
		private Organization organization;
		
	}

}
