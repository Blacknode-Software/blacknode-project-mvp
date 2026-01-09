package software.blacknode.backend.domain.role.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.meta.create.RoleCreationMeta;

@Getter
@Builder
public class RoleCustomCreationMeta implements RoleCreationMeta {

	private String name;
	private String description;
	private String color;
	
	private Role.Scope scope;
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
	
	public Optional<String> getColor() {
		return Optional.ofNullable(color);
	}
	
}
