package software.blacknode.backend.application.role.usecase;

import org.springframework.stereotype.Service;

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
	public Result execute(RoleCreateCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(organizationId, 
				memberId, AccessLevel.MANAGE);
		
		var meta = RoleCustomCreationMeta.builder()
				.name(command.getName())
				.description(command.getDescription())
				.color(command.getColor())
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
