package software.blacknode.backend.domain.modifier.delete;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.EntityModifier;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.meta.EntityModifierMeta;

public interface Deletable extends EntityModifier {

	Timestamp getDeletationTimestamp();

	void delete(Optional<DeletionMeta> meta);

	default void delete(DeletionMeta meta) {
		delete(Optional.of(meta));
	}

	default void delete() {
		delete(Optional.empty());
	}
	
	public default boolean isDeleted() {
		return getDeletationTimestamp() != null;
	}
	
	public default void ensureIsNotDeleted(EntityModifierMeta meta) {
		if(isDeleted()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is deleted. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	public default void ensureIsDeleted(EntityModifierMeta meta) {
		if(!isDeleted()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is not deleted. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	public default void ensureDeletionMetaProvided(Optional<DeletionMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Deletion meta must be provided when deleting " + this.getClass().getSimpleName() + ".");
		}
	}
	
	public default void throwUnsupportedDeletionMetaType(DeletionMeta meta) {
		throw new BlacknodeException("Unsupported DeletionMeta type for " + this.getClass().getSimpleName() + " deletion: " + meta.getClass().getName());
	}

}