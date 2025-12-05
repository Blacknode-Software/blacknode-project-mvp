package software.blacknode.backend.domain.modifier.delete;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;

public interface Deletable {

	Timestamp getDeletationTimestamp();

	void delete(Optional<DeletionMeta> meta);

	default void delete(DeletionMeta meta) {
		delete(Optional.of(meta));
	}

	default void delete() {
		delete(Optional.empty());
	}

}