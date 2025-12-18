package software.blacknode.backend.domain.entity.modifier.delete;

import java.util.Optional;

import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.DomainEntityModifier;
import software.blacknode.backend.domain.entity.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.meta.DomainEntityModifierMeta;
import software.blacknode.backend.domain.entity.modifier.meta.impl.EmptyModifierMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface Deletable extends DomainEntityModifier {

	Timestamp getDeletationTimestamp();

	void delete(Optional<DeletionMeta> meta);

	default void delete(DeletionMeta meta) {
		delete(Optional.of(meta));
	}

	default void delete() {
		delete(Optional.empty());
	}
	
	default boolean isDeleted() {
		return getDeletationTimestamp() != null;
	}
	
	default void ensureNotDeleted(Optional<? extends DomainEntityModifierMeta> meta) {
		ensureNotDeleted(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureNotDeleted(DomainEntityModifierMeta meta) {
		if(isDeleted()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is deleted. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	default void ensureDeleted(Optional<? extends  DomainEntityModifierMeta> meta) {
		ensureDeleted(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureDeleted(DomainEntityModifierMeta meta) {
		if(!isDeleted()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is not deleted. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	default void ensureDeletionMetaProvided(Optional<? extends DeletionMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Deletion meta must be provided when deleting " + this.getClass().getSimpleName() + ".");
		}
	}
	
	default void throwUnsupportedDeletionMeta(Optional<? extends DeletionMeta> meta) {
		throwUnsupportedDeletionMeta(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void throwUnsupportedDeletionMeta(DomainEntityModifierMeta meta) {
		throw new BlacknodeException("Unsupported DeletionMeta type for " + this.getClass().getSimpleName() + " deletion: " + meta.getClass().getName());
	}

}