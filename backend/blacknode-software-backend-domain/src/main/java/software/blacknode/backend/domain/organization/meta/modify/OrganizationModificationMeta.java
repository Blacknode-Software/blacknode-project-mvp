package software.blacknode.backend.domain.organization.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface OrganizationModificationMeta extends ModificationMeta {

	default Optional<String> getName() { return Optional.empty(); }
	
}
