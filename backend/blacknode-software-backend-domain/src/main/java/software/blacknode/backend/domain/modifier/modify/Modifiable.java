package software.blacknode.backend.domain.modifier.modify;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public interface Modifiable {

	Timestamp getModificationTimestamp();

	void modify(Optional<ModificationMeta> meta);

	default void modify(ModificationMeta modification) {
		modify(Optional.of(modification));
	}

	default void modify() {
		modify(Optional.empty());
	}

}