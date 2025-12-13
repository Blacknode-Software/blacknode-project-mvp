package software.blacknode.backend.domain.modifier.modify;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.EntityModifier;
import software.blacknode.backend.domain.modifier.meta.EntityModifierMeta;
import software.blacknode.backend.domain.modifier.meta.impl.EmptyModifierMeta;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public interface Modifiable extends EntityModifier {

	Timestamp getModificationTimestamp();

	void modify(Optional<ModificationMeta> meta);

	default void modify(ModificationMeta modification) {
		modify(Optional.of(modification));
	}

	default void modify() {
		modify(Optional.empty());
	}
	
	default boolean isModified() {
		return getModificationTimestamp() != null;
	}
	
	default void ensureInNotModified(Optional<? extends EntityModifierMeta> meta) {
		ensureIsNotModified(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureIsNotModifier(Optional<? extends EntityModifierMeta> meta) {
		ensureIsModified(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureIsNotModified(EntityModifierMeta meta) {
		if(isModified()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is modified. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	default void ensureIsModified(Optional<? extends EntityModifierMeta> meta) {
		ensureIsModified(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureIsModified(EntityModifierMeta meta) {
		if(!isModified()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is not modified. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	default void ensureModificationMetaProvided(Optional<? extends ModificationMeta> meta) {
		if(meta.isEmpty()) {
			throw new BlacknodeException("Modification meta must be provided when modifying a " + this.getClass().getSimpleName() + ".");
		}
	}
	
	default void throwUnsupportedModificationMeta(Optional<? extends ModificationMeta> meta) {
		throwUnsupportedModificationMeta(meta.map(m -> (EntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void throwUnsupportedModificationMeta(EntityModifierMeta meta) {
		throw new BlacknodeException("Unsupported ModificationMeta type for " + this.getClass().getSimpleName() + " modification: " + meta.getClass().getName());
	}


}