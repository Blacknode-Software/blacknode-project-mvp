package software.blacknode.backend.domain.modifier.duplicate;

import java.util.Optional;

import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.EntityModifier;
import software.blacknode.backend.domain.modifier.duplicate.meta.DuplicationMeta;
import software.blacknode.backend.domain.modifier.meta.EntityModifierMeta;
import software.blacknode.backend.domain.modifier.meta.impl.EmptyModifierMeta;

public interface Duplicatable extends EntityModifier {

    void duplicate(Optional<DuplicationMeta> meta);

    void duplicate(DuplicationMeta meta);

    void duplicate();
    
    default void ensureDuplicationMetaProvided(Optional<? extends DuplicationMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Duplication meta must be provided when deleting " + this.getClass().getSimpleName() + ".");
		}
	}

    default void throwUnsupportedDuplicationMetaType(Optional<? extends DuplicationMeta> meta0) {
		throwUnsupportedDuplicationMetaType(meta0.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
    }
    
    default void throwUnsupportedDuplicationMetaType(EntityModifierMeta meta) {
    	throw new BlacknodeException("Unsupported DuplicationMeta type for " + this.getClass().getSimpleName() + " duplication: " + meta.getClass().getName());
    }
}