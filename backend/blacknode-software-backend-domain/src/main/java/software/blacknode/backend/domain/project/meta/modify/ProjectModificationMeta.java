package software.blacknode.backend.domain.project.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface ProjectModificationMeta extends ModificationMeta {

	default Optional<String> getName() { return Optional.empty(); }
	default Optional<String> getDescription() { return Optional.empty(); }
	default Optional<String> getColor() { return Optional.empty(); }
	
}
