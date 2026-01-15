package software.blacknode.backend.application.role.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.patch.impl.PatchOperationEnum;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.role.command.RolePatchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.meta.modify.impl.RoleColorModificationMeta;
import software.blacknode.backend.domain.role.meta.modify.impl.RoleDescriptionModificationMeta;
import software.blacknode.backend.domain.role.meta.modify.impl.RoleNameModificationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class RolePatchUseCase implements ResultExecutionUseCase<RolePatchCommand, RolePatchUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(RolePatchCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		
		var roleId = command.getId();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(organizationId, 
				memberId, AccessLevel.MANAGE);
		
		var operations = command.getOperations();
		
		var modifications = ModificationMeta.emptyList();

		if(RolePatchOperation.NAME.isIn(operations)) {
			var name = command.getName();
			
			var meta = RoleNameModificationMeta.builder()
					.name(name)
					.build();
			
			modifications.add(meta);
		}
		
		if(RolePatchOperation.DESCRIPTION.isIn(operations)) {
			var description = command.getDescription();
			
			var meta = RoleDescriptionModificationMeta.builder()
					.description(description)
					.build();
			
			modifications.add(meta);
		}
		
		if(RolePatchOperation.COLOR.isIn(operations)) {
			var color = command.getColor();
			
			var meta = RoleColorModificationMeta.builder()
					.color(color)
					.build();
			
			modifications.add(meta);
		}
		
		var role = roleService.modify(organizationId, roleId, modifications);
		
		return Result.builder()
				.role(role)
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {

		@NonNull
		private final Role role;
		
	}
	
	public static enum RolePatchOperation implements PatchOperationEnum {
		NAME,
		DESCRIPTION,
		COLOR,
		;
	}

}
