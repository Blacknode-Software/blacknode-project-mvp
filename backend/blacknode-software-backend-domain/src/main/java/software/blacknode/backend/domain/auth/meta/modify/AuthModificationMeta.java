package software.blacknode.backend.domain.auth.meta.modify;

import java.util.Optional;

import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface AuthModificationMeta extends ModificationMeta {

	default Optional<AuthMethod> getAuthMethod() { return Optional.empty(); }
	
}
