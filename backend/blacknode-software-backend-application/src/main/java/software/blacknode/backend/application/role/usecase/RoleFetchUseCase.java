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
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.role.command.RoleFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class RoleFetchUseCase implements ResultExecutionUseCase<RoleFetchCommand, RoleFetchUseCase.Result> {
	
	private final OrganizationAccessControl organizationAccessControl;
	
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(RoleFetchCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(organizationId, memberId, AccessLevel.READ);
		
		var roleId = command.getRoleId();
		
		var role = roleService.getOrThrow(organizationId, roleId);
		
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

}
