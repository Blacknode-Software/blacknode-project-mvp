package software.blacknode.backend.domain.entity.modifier.impl.duplicate;

import java.util.Optional;

import software.blacknode.backend.domain.entity.modifier.DomainEntityModifier;
import software.blacknode.backend.domain.entity.modifier.impl.duplicate.meta.DuplicationMeta;
import software.blacknode.backend.domain.entity.modifier.meta.DomainEntityModifierMeta;
import software.blacknode.backend.domain.entity.modifier.meta.impl.EmptyModifierMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface Duplicatable extends DomainEntityModifier {

    void duplicate(Optional<DuplicationMeta> meta);

    void duplicate(DuplicationMeta meta);

    void duplicate();
    
    default void ensureDuplicationMetaProvided(Optional<? extends DuplicationMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Duplication meta must be provided when deleting " + this.getClass().getSimpleName() + ".");
		}
	}

    default void throwUnsupportedDuplicationMetaType(Optional<? extends DuplicationMeta> meta0) {
		throwUnsupportedDuplicationMetaType(meta0.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
    }
    
    default void throwUnsupportedDuplicationMetaType(DomainEntityModifierMeta meta) {
    	throw new BlacknodeException("Unsupported DuplicationMeta type for " + this.getClass().getSimpleName() + " duplication: " + meta.getClass().getName());
    }
}