package software.blacknode.backend.application.organization.usecase;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.AccessControlService;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.organization.OrganizationService;
import software.blacknode.backend.application.organization.command.OrganizationPatchCommand;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.meta.modify.impl.OrganizationNameModificationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class OrganizationPatchUseCase implements ResultExecutionUseCase<OrganizationPatchCommand, OrganizationPatchUseCase.Result> {

	private final OrganizationService organizationService;
	private final AccessControlService accessControlService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	public Result execute(OrganizationPatchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		accessControlService.ensureMemberHasOrganizationAccess(memberId, organizationId, AccessLevel.MANAGE);
		
		var operations = command.getOperations();
		
		var modifications = ModificationMeta.emptyList();
		
		if(OrganizationPatchOperation.NAME.isIn(operations)) {
			
			var name = command.getName();
			
			var meta = OrganizationNameModificationMeta.builder()
					.name(name)
					.build();
			
			modifications.add(meta);
		}
		
		var organization = organizationService.modify(organizationId, modifications);
		
		return Result.builder()
				.organization(organization)
				.build();
	}
	
	
	@Getter
	@Builder
	public static class Result {
	
		private final Organization organization;
		
	}

	public static enum OrganizationPatchOperation implements PatchOperationEnum {
		NAME,
		;
	}

}
