package software.blacknode.backend.application.role.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.role.command.RolesInOrganizationCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class RolesInOrganizationUseCase implements ResultExecutionUseCase<RolesInOrganizationCommand, RolesInOrganizationUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final RoleService roleService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public Result execute(RolesInOrganizationCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		var rolesIds = roleService.getAll(organizationId)
				.stream()
				.map(role -> role.getId())
				.toList();
		
		return Result.builder()
				.roleIds(rolesIds)
				.build();
	}
	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<HUID> roleIds;
		
	}

}
