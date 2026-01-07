package software.blacknode.backend.domain.role.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

@Getter
@Builder
public class RoleSuperPrivilegedModificationMeta implements RoleModificationMeta {
	
	private final Boolean superPrivileged;
	
	@Override
	public Optional<Boolean> isSuperPrivileged() {
		return Optional.ofNullable(superPrivileged);
	}

}
