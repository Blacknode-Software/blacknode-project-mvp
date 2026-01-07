package software.blacknode.backend.domain.role.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

@Getter
@Builder
public class RoleByDefaultAssignedModificationMeta implements RoleModificationMeta {
	
	private final Boolean byDefaultAssigned;
	
	@Override
	public Optional<Boolean> isByDefaultAssigned() {
		return Optional.ofNullable(byDefaultAssigned);
	}

}
