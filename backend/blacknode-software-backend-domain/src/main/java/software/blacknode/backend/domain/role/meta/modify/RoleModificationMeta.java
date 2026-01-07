package software.blacknode.backend.domain.role.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface RoleModificationMeta extends ModificationMeta {

	default Optional<String> getName() { return Optional.empty(); }
	
	default Optional<String> getDescription() { return Optional.empty(); }
	
	default Optional<String> getColor() { return Optional.empty(); }
	
	default Optional<Boolean> isSystemDefault() { return Optional.empty(); }
	
	default Optional<Boolean> isByDefaultAssigned() { return Optional.empty(); }
	
	default Optional<Boolean> isSuperPrivileged() { return Optional.empty(); }
	
}
