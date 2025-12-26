package software.blacknode.backend.domain.task.repository;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.Task;

public interface TaskRepository {

	Optional<Task> findById(HUID organizationId, HUID id);

	void save(HUID organizationId,Task task);
	
}
