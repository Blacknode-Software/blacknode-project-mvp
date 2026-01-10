package software.blacknode.backend.domain.task.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.Task;

public interface TaskRepository {

	Optional<Task> findById(HUID organizationId, HUID id);
	
	List<Task> findByChannelId(HUID organizationId, HUID channelId);
	
	List<Task> findByIds(HUID organizationId, Set<HUID> ids);

	void save(HUID organizationId,Task task);
	
}
