package software.blacknode.backend.application.task;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.meta.delete.impl.TaskDefaultDeletionMeta;
import software.blacknode.backend.domain.task.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository repository;
	
	public Task create(HUID organizationId, CreationMeta meta) {
		var task = new Task(organizationId);
		
		task.create(meta);
		
		repository.save(organizationId, task);
		
		return task;
	}
	
	public Task modify(HUID organizationId, HUID taskId, ModificationMeta meta) {
		return modify(organizationId, taskId, List.of(meta));
	}
	
	public Task modify(HUID organizationId, HUID taskId, List<ModificationMeta> metas) {
		var task = getOrThrow(organizationId, taskId);
		
		for(var meta : metas) {
			task.modify(meta);
		}
		
		repository.save(organizationId, task);
		
		return task;
	}
	
	public void delete(HUID organizationId, HUID taskId) {
		var meta = TaskDefaultDeletionMeta.builder().build();
		
		delete(organizationId, taskId, meta);
	}
	
	public void delete(HUID organizationId, HUID taskId, DeletionMeta meta) {
		var task = getOrThrow(organizationId, taskId);
		
		task.delete(meta);
		
		repository.save(organizationId, task);
	}
	
	public Task getOrThrow(HUID organizationId, HUID taskId) {
		return repository.findById(organizationId, taskId)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));
	}
	
	public List<Task> getByIds(HUID organizationId, List<HUID> taskIds) {
		// Implementation to list tasks by their IDs
		return List.of(); // Placeholder
	}
	
	public List<Task> getAllInChannel(HUID organizationId, HUID channelId) {
		// Implementation to list tasks by channel ID
		return List.of(); // Placeholder
	}
}
