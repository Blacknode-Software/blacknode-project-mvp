package software.blacknode.backend.domain.modifier.create;

import java.util.Optional;

import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.EntityModifier;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.meta.EntityModifierMeta;
import software.blacknode.backend.domain.modifier.meta.impl.EmptyModifierMeta;

public interface Creatable extends EntityModifier {

    Timestamp getCreationTimestamp();
    
    void create(Optional<CreationMeta> meta);

    default void create(CreationMeta meta) {
		create(Optional.of(meta));
	}
    
    default void create() {
    	create(Optional.empty());
    }
    
    default boolean isCreated() {
		return getCreationTimestamp() != null;
	}
    
    default void ensureCreated(Optional<? extends EntityModifierMeta> meta) {
    	ensureCreated(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
    }
    
    default void ensureCreated(EntityModifierMeta meta) {
		if(!isCreated()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is not created yet. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
    
    default void ensureNotCreated(Optional<? extends EntityModifierMeta> meta) {
		ensureNotCreated(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
    
    default void ensureNotCreated(EntityModifierMeta meta) {
		if(isCreated()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is already created. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
    }
    
    default void ensureCreationMetaProvided(Optional<? extends CreationMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Creation meta must be provided when creating a " + this.getClass().getSimpleName() + ".");
		}
    }
    
    default void throwUnspportedCreationMeta(Optional<? extends CreationMeta> meta) {
    	throwUnsupportedCreationMeta(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
    }
    
    default void throwUnsupportedCreationMeta(EntityModifierMeta meta) {
    	throw new BlacknodeException("Unsupported CreationMeta type for " + this.getClass().getSimpleName() + " creation: " + meta.getClass().getName());
    }

}