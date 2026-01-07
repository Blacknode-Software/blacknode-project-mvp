package software.blacknode.backend.domain.role.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

@Getter
@Builder
public class RoleSystemDefaultModificationMeta implements RoleModificationMeta {
	
	private final Boolean systemDefault;
	
	@Override
	public Optional<Boolean> isSystemDefault() {
		return Optional.ofNullable(systemDefault);
	}

}
