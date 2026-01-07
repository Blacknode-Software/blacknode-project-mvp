package software.blacknode.backend.domain.role.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

@Getter
@Builder
public class RoleDescriptionModificationMeta implements RoleModificationMeta {
	
	private final String description;
	
	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}

}
