package software.blacknode.backend.domain.modifier.delete;

import java.util.Optional;

import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.EntityModifier;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.meta.EntityModifierMeta;
import software.blacknode.backend.domain.modifier.meta.impl.EmptyModifierMeta;

public interface Deletable extends EntityModifier {

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
	
	default void ensureNotDeleted(Optional<? extends EntityModifierMeta> meta) {
		ensureNotDeleted(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureNotDeleted(EntityModifierMeta meta) {
		if(isDeleted()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is deleted. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	default void ensureDeleted(Optional<? extends  EntityModifierMeta> meta) {
		ensureDeleted(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureDeleted(EntityModifierMeta meta) {
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
		throwUnsupportedDeletionMeta(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void throwUnsupportedDeletionMeta(EntityModifierMeta meta) {
		throw new BlacknodeException("Unsupported DeletionMeta type for " + this.getClass().getSimpleName() + " deletion: " + meta.getClass().getName());
	}

}