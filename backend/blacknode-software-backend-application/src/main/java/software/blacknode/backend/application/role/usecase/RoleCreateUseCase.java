package software.blacknode.backend.application.role.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.role.command.RoleCreateCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.role.meta.create.impl.RoleCustomCreationMeta;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class RoleCreateUseCase implements ResultExecutionUseCase<RoleCreateCommand, RoleCreateUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(RoleCreateCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(organizationId, 
				memberId, AccessLevel.MANAGE);
		
		var name = command.getName();
		var description = command.getDescription();
		var color = command.getColor();
		
		var inheritedRoleId = command.getInheritedRoleId();
		var inheritedRole = roleService.getOrThrow(organizationId, inheritedRoleId);
		
		var scope = inheritedRole.getScope();
		var superPrivileged = inheritedRole.getMeta().isSuperPrivileged();
		
		var meta = RoleCustomCreationMeta.builder()
				.name(name)
				.description(description)
				.color(color)
				.scope(scope)
				.superPrivileged(superPrivileged)
				.build();
		
		var role = roleService.create(organizationId, meta);
		
		return Result.builder()
				.roleId(role.getId())
				.build();
	}

	@Getter
	@Builder
	@ToString
	public static class Result {
	
		@NonNull
		private final HUID roleId;
		
	}

}
