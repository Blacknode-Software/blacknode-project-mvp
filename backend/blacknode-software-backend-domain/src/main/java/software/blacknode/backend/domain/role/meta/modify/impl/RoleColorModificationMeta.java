package software.blacknode.backend.domain.role.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

@Getter
@Builder
public class RoleColorModificationMeta implements RoleModificationMeta {
	
	private final String color;
	
	@Override
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}

}
