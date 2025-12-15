package software.blacknode.backend.infrastructure.task;

import java.util.Optional;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.repository.TaskRepository;

public class TaskRepositoryImpl implements TaskRepository {

	@Override
	public Optional<Task> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Task task) {
		// TODO Auto-generated method stub
		
	}

}
