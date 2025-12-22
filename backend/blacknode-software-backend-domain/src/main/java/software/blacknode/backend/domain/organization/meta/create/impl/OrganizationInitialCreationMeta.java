package software.blacknode.backend.domain.organization.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.organization.meta.create.OrganizationCreationMeta;

@Builder
@ToString
public class OrganizationInitialCreationMeta implements OrganizationCreationMeta {

	private String name;
	
	@Override
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
}
