package software.blacknode.backend.application.task.assign;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.task.assign.TaskAssign;
import software.blacknode.backend.domain.task.assign.meta.delete.impl.TaskAssignDefaultDeletionMeta;
import software.blacknode.backend.domain.task.assign.repository.TaskAssignRepository;

@Service
@RequiredArgsConstructor
public class TaskAssignService {

	private final TaskAssignRepository repository;
	
	public TaskAssign create(HUID organizationId, CreationMeta meta) {
		var taskAssign = new TaskAssign(organizationId);
		
		taskAssign.create(meta);
		
		repository.save(organizationId, taskAssign);
		
		return taskAssign;
	}
	
	public void delete(HUID organizationId, HUID id) {
		var meta = TaskAssignDefaultDeletionMeta.builder().build();
		
		delete(organizationId, id, meta);
	}
	
	public void delete(HUID organizationId, HUID id, DeletionMeta meta) {
		var taskAssign = getOrThrow(organizationId, id);
		
		taskAssign.delete(meta);
		
		repository.save(organizationId, taskAssign);
	}
	
		
	public TaskAssign getOrThrow(HUID organizationId, HUID id) {
		return get(organizationId, id).orElseThrow(() -> 
				new BlacknodeException("TaskAssign with id " + id + " not found in organization " + organizationId));
	}
	
	public Optional<TaskAssign> get(HUID organizationId, HUID id) {
		return repository.findById(organizationId, id);
	}
	
	public Optional<TaskAssign> getByMemberIdAndTaskId(HUID organizationId, HUID taskId, HUID memberId) {
		return repository.findByMemberIdAndTaskId(organizationId, taskId, memberId);
	}
	
	public List<TaskAssign> getAll(HUID organizationId) {
		return repository.findAll(organizationId);
	}
	
	public List<TaskAssign> getByIds(HUID organizationId, List<HUID> ids) {
		return getByIds(organizationId, Set.copyOf(ids));
	}
	
	public List<TaskAssign> getByIds(HUID organizationId, Set<HUID> ids) {
		return repository.findByIds(organizationId, ids);
	}
	
	public List<TaskAssign> getByTaskId(HUID organizationId, HUID taskId) {
		return repository.findByTaskId(organizationId, taskId);
	}
	
	public List<TaskAssign> getByMemberId(HUID organizationId, HUID memberId) {
		return repository.findByMemberId(organizationId, memberId);
	}
	
	public List<TaskAssign> getByTaskIds(HUID organizationId, List<HUID> taskIds) {
		return getByTaskIds(organizationId, Set.copyOf(taskIds));
	}
	
	public List<TaskAssign> getByTaskIds(HUID organizationId, Set<HUID> taskIds) {
		return repository.findByTaskIds(organizationId, taskIds);
	}
	
	public List<TaskAssign> getByMemberIds(HUID organizationId, List<HUID> memberIds) {
		return getByMemberIds(organizationId, Set.copyOf(memberIds));
	}
	
	public List<TaskAssign> getByMemberIds(HUID organizationId, Set<HUID> memberIds) {
		return repository.findByMemberIds(organizationId, memberIds);
	}
}
