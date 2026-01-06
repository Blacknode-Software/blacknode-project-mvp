package software.blacknode.backend.domain.role.meta.create;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.role.Role;

public interface RoleCreationMeta extends CreationMeta {

	default Optional<String> getName() { return Optional.empty(); }
	
	default Optional<String> getDescription() { return Optional.empty(); }
	
	default Optional<String> getColor() { return Optional.empty(); }
	
	default Optional<Boolean> isSystemDefault() { return Optional.empty(); }
	
	default Optional<Boolean> isByDefaultAssigned() { return Optional.empty(); }
	
	default Optional<Boolean> isSuperPrivileged() { return Optional.empty(); }
	
	Role.Scope getScope();
	
}
