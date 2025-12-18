package software.blacknode.backend.domain.entity.modifier.impl.modify;

import java.util.Optional;

import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.DomainEntityModifier;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.entity.modifier.meta.DomainEntityModifierMeta;
import software.blacknode.backend.domain.entity.modifier.meta.impl.EmptyModifierMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface Modifiable extends DomainEntityModifier {

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
	
	default void ensureInNotModified(Optional<? extends DomainEntityModifierMeta> meta) {
		ensureIsNotModified(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureIsNotModifier(Optional<? extends DomainEntityModifierMeta> meta) {
		ensureIsModified(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureIsNotModified(DomainEntityModifierMeta meta) {
		if(isModified()) {
			throw new BlacknodeException(this.getClass().getSimpleName() + " is modified. Cannot perform " + meta.getClass().getSimpleName() + " operation.");
		}
	}
	
	default void ensureIsModified(Optional<? extends DomainEntityModifierMeta> meta) {
		ensureIsModified(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void ensureIsModified(DomainEntityModifierMeta meta) {
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
		throwUnsupportedModificationMeta(meta.map(m -> (DomainEntityModifierMeta) m).orElse(new EmptyModifierMeta()));
	}
	
	default void throwUnsupportedModificationMeta(DomainEntityModifierMeta meta) {
		throw new BlacknodeException("Unsupported ModificationMeta type for " + this.getClass().getSimpleName() + " modification: " + meta.getClass().getName());
	}


}