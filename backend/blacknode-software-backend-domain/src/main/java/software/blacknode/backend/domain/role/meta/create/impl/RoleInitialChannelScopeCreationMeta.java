package software.blacknode.backend.domain.role.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.meta.create.RoleCreationMeta;

@Getter
@Builder
public class RoleInitialChannelScopeCreationMeta implements RoleCreationMeta {

	private String name;
	private String description;
	private String color;
	
	private boolean byDefaultAssigned;
	private boolean superPrivileged;
	private boolean systemDefault;
	
	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
	@Override
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
	
	@Override
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}
	
	@Override
	public Optional<Boolean> isByDefaultAssigned() {
		return Optional.of(byDefaultAssigned);
	}
	
	@Override
	public Optional<Boolean> isSuperPrivileged() {
		return Optional.of(superPrivileged);
	}
	
	@Override
	public Optional<Boolean> isSystemDefault() {
		return Optional.of(systemDefault);
	}
	
	@Override
	public Role.Scope getScope() {
		return Role.Scope.CHANNEL;
	}
}
