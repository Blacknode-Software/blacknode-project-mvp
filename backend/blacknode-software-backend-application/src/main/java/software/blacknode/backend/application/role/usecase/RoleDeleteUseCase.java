package software.blacknode.backend.application.role.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.role.command.RoleDeleteCommand;
import software.blacknode.backend.application.shared.SharedDeletionService;
import software.blacknode.backend.application.usecase.ExecutionUseCase;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Service
@RequiredArgsConstructor
public class RoleDeleteUseCase implements ExecutionUseCase<RoleDeleteCommand> {
	
	private final OrganizationAccessControl organizationAccessControl;

	private final RoleService roleService;

	private final SharedDeletionService sharedDeletionService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	@Transactional
	public void execute(RoleDeleteCommand command) {
		var organizationId = sessionContextHolder.getOrganizationIdOrThrow();
		var memberId = sessionContextHolder.getMemberIdOrThrow();
		
		organizationAccessControl.ensureMemberHasOrganizationAccess(organizationId, 
				memberId, AccessLevel.MANAGE);
		
		var roleId = command.getRoleId();
		var role = roleService.getOrThrow(organizationId, roleId);
		
		if(role.getMeta().isSystemDefault()) {
			throw new BlacknodeException("Cannot delete system default role: " + role.getMeta().getName());
		}
		
		sharedDeletionService.deleteRoleCascade(organizationId, roleId);
	}

}
