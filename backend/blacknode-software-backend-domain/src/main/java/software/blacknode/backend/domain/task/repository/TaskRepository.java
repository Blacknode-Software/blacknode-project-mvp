package software.blacknode.backend.domain.task.repository;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.Task;

public interface TaskRepository {

	Optional<Task> findById(HUID id);

	void save(Task task);
}
