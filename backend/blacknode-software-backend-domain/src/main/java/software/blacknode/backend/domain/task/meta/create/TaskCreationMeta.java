package software.blacknode.backend.domain.task.meta.create;

import java.util.Optional;

import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface TaskCreationMeta extends CreationMeta {

	default Optional<String> getTitle() { return Optional.empty(); };
	default Optional<String> getDescription() { return Optional.empty(); };
	
	default Optional<Integer> getPriority() { return Optional.empty(); };
	
	default Optional<Timestamp> getBeginAtTimestamp() { return Optional.empty(); };
	default Optional<Timestamp> getEndAtTimestamp() { return Optional.empty(); };
	
	default Optional<HUID> getStatusId() { return Optional.empty(); };
	
	HUID getOwnerMemberId();
	HUID getChannelId();
	
}
