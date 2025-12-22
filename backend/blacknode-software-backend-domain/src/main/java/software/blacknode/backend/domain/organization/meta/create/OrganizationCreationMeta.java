package software.blacknode.backend.domain.organization.meta.create;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface OrganizationCreationMeta extends CreationMeta {

	default Optional<String> getName() { return Optional.empty(); }
	
}
