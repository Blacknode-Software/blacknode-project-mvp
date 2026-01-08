package software.blacknode.backend.application.organization.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.organization.command.OrganizationFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class OrganizationFetchUseCase implements ResultExecutionUseCase<OrganizationFetchCommand, OrganizationFetchUseCase.Result>{

	private final OrganizationService organizationService;
	
	private final OrganizationAccessControl organizationAccessControl;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(OrganizationFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(memberId, 
				organizationId, AccessLevel.READ);
		
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
