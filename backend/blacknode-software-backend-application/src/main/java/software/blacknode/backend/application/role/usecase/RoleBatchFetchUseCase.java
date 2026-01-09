package software.blacknode.backend.application.role.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.application.access.impl.OrganizationAccessControl;
import software.blacknode.backend.application.role.RoleService;
import software.blacknode.backend.application.role.command.RoleBatchFetchCommand;
import software.blacknode.backend.application.usecase.ResultExecutionUseCase;
import software.blacknode.backend.domain.role.Role;

@Service
@RequiredArgsConstructor
public class RoleBatchFetchUseCase implements ResultExecutionUseCase<RoleBatchFetchCommand, RoleBatchFetchUseCase.Result> {

	private final OrganizationAccessControl organizationAccessControl;
	
	private final RoleService roleService;
	
	@Override
	public Result execute(RoleBatchFetchCommand command) {
		
		
	}

	
	@Getter
	@Builder
	@ToString
	public static class Result {
		
		@NonNull
		private final List<Role> roles;
	
	}

}
