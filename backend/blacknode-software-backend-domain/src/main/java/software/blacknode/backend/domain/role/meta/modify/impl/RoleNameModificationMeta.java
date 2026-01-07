package software.blacknode.backend.domain.role.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

@Getter
@Builder
public class RoleNameModificationMeta implements RoleModificationMeta {
	
	private final String name;
	
	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

}
