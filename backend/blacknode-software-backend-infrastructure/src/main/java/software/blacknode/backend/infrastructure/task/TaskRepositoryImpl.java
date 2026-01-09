package software.blacknode.backend.infrastructure.task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.repository.TaskRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;
import software.blacknode.backend.infrastructure.task.entity.TaskEntity;
import software.blacknode.backend.infrastructure.task.entity.mapper.TaskEntityMapper;
import software.blacknode.backend.infrastructure.task.entity.repository.TaskEntityRepository;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository, OrganizationRelatedEntityRepository<Task, TaskEntity> {

	private final TaskEntityRepository repository;
	private final TaskEntityMapper mapper;
	
	
	@Override
	public Optional<Task> findById(@NonNull HUID organizationId, HUID id) {
		var task = repository.queryByIdAndOrganizationIdAndState(id.toUUID(), 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return task.map(this::toDomainEntity);
	}
	
	@Override
	public List<Task> findByIds(@NonNull HUID organizationId, @NonNull Set<HUID> ids) {
		var uuidList = ids.stream().map(HUID::toUUID).toList();
		
		var tasks = repository.queryAllByIdInAndOrganizationIdAndState(uuidList, 
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return tasks.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<Task> findByChannelId(@NonNull HUID organizationId, @NonNull HUID channelId) {
		var tasks = repository.queryAllByChannelIdAndOrganizationIdAndState(
				channelId.toUUID(), organizationId.toUUID(), EntityState.ACTIVE);
		
		return tasks.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(@NonNull HUID organizationId, @NonNull Task task) {
		task.ensureBelongsToOrganization(organizationId);
		
		var taskEntity = toInfrastructureEntity(task);
		
		repository.save(taskEntity);
		
	}

	@Override
	public TaskEntity toInfrastructureEntity(Task domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public Task toDomainEntity(TaskEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}
	

}
