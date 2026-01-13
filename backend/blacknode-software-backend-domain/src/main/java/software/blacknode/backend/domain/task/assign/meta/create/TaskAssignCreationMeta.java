package software.blacknode.backend.domain.task.assign.meta.create;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface TaskAssignCreationMeta extends CreationMeta {
	
	default Optional<HUID> getAssignerId() { return Optional.empty(); }

	HUID getTaskId();
	
	HUID getMemberId();
	
}
