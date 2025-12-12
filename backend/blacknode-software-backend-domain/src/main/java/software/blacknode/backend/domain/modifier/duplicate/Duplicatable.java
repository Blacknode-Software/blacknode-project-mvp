package software.blacknode.backend.domain.modifier.duplicate;

import java.util.Optional;

import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.EntityModifier;
import software.blacknode.backend.domain.modifier.duplicate.meta.DuplicationMeta;

public interface Duplicatable extends EntityModifier {

    void duplicate(Optional<DuplicationMeta> meta);

    void duplicate(DuplicationMeta meta);

    void duplicate();
    
    public default void ensureDuplicationMetaProvided(Optional<DuplicationMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Duplication meta must be provided when deleting " + this.getClass().getSimpleName() + ".");
		}
	}

    public default void throwUnsupportedDuplicationMetaType(DuplicationMeta meta) {
    	throw new BlacknodeException("Unsupported DuplicationMeta type for " + this.getClass().getSimpleName() + " duplication: " + meta.getClass().getName());
    }
}