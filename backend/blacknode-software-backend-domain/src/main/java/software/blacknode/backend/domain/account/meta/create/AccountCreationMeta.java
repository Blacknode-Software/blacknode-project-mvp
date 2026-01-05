package software.blacknode.backend.domain.account.meta.create;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface AccountCreationMeta extends CreationMeta {

	default Optional<String> getLastName() { return Optional.empty(); }
	default Optional<String> getFirstName() { return Optional.empty(); }
	
	String getEmail();
	
}
