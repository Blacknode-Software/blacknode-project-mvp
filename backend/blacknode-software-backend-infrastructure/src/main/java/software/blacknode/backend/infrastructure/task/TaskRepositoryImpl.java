package software.blacknode.backend.infrastructure.task;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.repository.TaskRepository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

	@Override
	public Optional<Task> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(HUID organizationId, Task task) {
		// TODO Auto-generated method stub
		
	}



}
