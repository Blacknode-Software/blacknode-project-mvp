package software.blacknode.backend.application.role.usecase;

import java.util.List;

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
import software.blacknode.backend.application.role.command.RolesBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class RolesBatchFetchUseCase implements ResultExecutionUseCase<RolesBatchFetchCommand, RolesBatchFetchUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(RolesBatchFetchCommand command) {
		sessionContextHolder.ensureOrganizationScoped();
		
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(organizationId, memberId, AccessLevel.READ);
		
		var rolesIds = command.getRoleIds();
		
		var roles = roleService.getByIds(organizationId, rolesIds);
		
		return Result.builder()
				.roles(roles)
				.build();
	}

	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<Role> roles;
	
	}

}
