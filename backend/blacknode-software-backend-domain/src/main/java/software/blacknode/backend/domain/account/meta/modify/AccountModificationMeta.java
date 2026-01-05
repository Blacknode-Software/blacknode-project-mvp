package software.blacknode.backend.domain.account.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface AccountModificationMeta extends ModificationMeta {

	default Optional<String> getFirstName() { return Optional.empty(); }
	default Optional<String> getLastName() { return Optional.empty(); }
	
}
