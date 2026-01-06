package software.blacknode.backend.application.task;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.meta.delete.impl.TaskDefaultDeletionMeta;
import software.blacknode.backend.domain.task.repository.TaskRepository;

@Transactional
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
	
	public Optional<Task> get(HUID organizationId, HUID taskId) {
		return repository.findById(organizationId, taskId);
	}
	
	public Task getOrThrow(HUID organizationId, HUID taskId) {
		return get(organizationId, taskId).orElseThrow(() -> 
			new BlacknodeException("Task with id " + taskId + " not found")
		);
	}
	
	public List<Task> getByIds(HUID organizationId, List<HUID> taskIds) {
		return repository.findByIds(organizationId, taskIds);
	}
	
	public List<Task> getAllInChannel(HUID organizationId, HUID channelId) {
		return repository.findByChannelId(organizationId, channelId);
	}
}
