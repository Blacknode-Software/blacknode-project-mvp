package software.blacknode.backend.domain.organization.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import software.blacknode.backend.domain.organization.meta.modify.OrganizationModificationMeta;

@Builder
public class OrganizationNameModificationMeta implements OrganizationModificationMeta {

	private final String name;
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
}
