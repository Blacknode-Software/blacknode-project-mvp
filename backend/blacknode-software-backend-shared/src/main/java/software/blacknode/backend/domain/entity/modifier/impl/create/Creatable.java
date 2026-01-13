package software.blacknode.backend.domain.entity.modifier.impl.create;

import java.util.Optional;

import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.DomainEntityModifier;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.meta.DomainEntityModifierMeta;
import software.blacknode.backend.domain.entity.modifier.meta.impl.EmptyModifierMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface Creatable extends DomainEntityModifier {

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
    
    default void ensureCreated(Optional<? extends DomainEntityModifierMeta> meta) {
    	ensureCreated(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
    }
    
    default void ensureCreated(DomainEntityModifierMeta meta) {
		if(!isCreated()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is not created yet. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
    
    default void ensureNotCreated(Optional<? extends DomainEntityModifierMeta> meta) {
		ensureNotCreated(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
    
    default void ensureNotCreated(DomainEntityModifierMeta meta) {
		if(isCreated()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is already created. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
    }
    
    default void ensureCreationMetaProvided(Optional<? extends CreationMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Creation meta must be provided when creating a " + this.getClass().getSimpleName() + ".");
		}
    }
    
    default void throwUnsupportedCreationMeta(Optional<? extends CreationMeta> meta) {
    	throwUnsupportedCreationMeta(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
    }
    
    default void throwUnsupportedCreationMeta(DomainEntityModifierMeta meta) {
    	throw new BlacknodeException("Unsupported CreationMeta type for " + this.getClass().getSimpleName() + " creation: " + meta.getClass().getName());
    }

}