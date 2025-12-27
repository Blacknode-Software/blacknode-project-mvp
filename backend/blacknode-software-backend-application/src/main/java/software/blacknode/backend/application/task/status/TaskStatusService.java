package software.blacknode.backend.application.task.status;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.task.status.TaskStatus;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
	
	public Optional<TaskStatus> get(HUID organizationId, HUID id) {
		TaskStatus status = TaskStatus.PREDEFINED_STATUSES.get(id);
		
		return Optional.ofNullable(status);
	}
	
	public TaskStatus getOrThrow(HUID organizationId, HUID id) {
		return get(organizationId, id).orElseThrow(() -> 
			new BlacknodeException("TaskStatus with id " + id + " not found")
		);
	}
	
	public List<TaskStatus> getAllInChannel(HUID organizationId, HUID channelId) {
		return List.copyOf(TaskStatus.PREDEFINED_STATUSES.values());
	}
	
	public List<TaskStatus> getByIds(HUID organizationId, List<HUID> ids) {
		return ids.stream()
				.map(id -> get(organizationId, id))
				.filter(status -> status.isPresent())
				.map(status -> status.get())
				.toList();
	}
	
}
