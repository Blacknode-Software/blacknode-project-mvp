package software.blacknode.backend.domain.task.meta.modify;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public interface TaskModificationMeta extends ModificationMeta {

	default Optional<String> getTitle() { return Optional.empty(); };
	default Optional<String> getDescription() { return Optional.empty(); };
	
	default boolean isPrioritySet() { return false; };
	default Optional<Integer> getPriority() { return Optional.empty(); };
	
	default boolean isBeginAtTimestampSet() { return false; };
	default Optional<Timestamp> getBeginAtTimestamp() { return Optional.empty(); };
	
	default boolean isEndAtTimestampSet() { return false; };
	default Optional<Timestamp> getEndAtTimestamp() { return Optional.empty(); };
	
	default Optional<HUID> getStatusId() { return Optional.empty(); };
	
}
