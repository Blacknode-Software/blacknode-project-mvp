package software.blacknode.backend.infrastructure.task.assign;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.assign.TaskAssign;
import software.blacknode.backend.domain.task.assign.repository.TaskAssignRepository;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.organization.related.repository.OrganizationRelatedEntityRepository;
import software.blacknode.backend.infrastructure.task.assign.entity.TaskAssignEntity;
import software.blacknode.backend.infrastructure.task.assign.entity.mapper.TaskAssignEntityMapper;
import software.blacknode.backend.infrastructure.task.assign.entity.repository.TaskAssignEntityRepository;

@Repository
@RequiredArgsConstructor
public class TaskAssignRepositoryImpl implements TaskAssignRepository, OrganizationRelatedEntityRepository<TaskAssign, TaskAssignEntity> {

	private final TaskAssignEntityRepository repository;
	private final TaskAssignEntityMapper mapper;
	
	
	@Override
	public Optional<TaskAssign> findById(HUID organizationId, HUID id) {
		var taskAssign = repository.queryByIdAndOrganizationIdAndState(
				id.toUUID(), organizationId.toUUID(), EntityState.ACTIVE);
		
		return taskAssign.map(this::toDomainEntity);
	}

	@Override
	public List<TaskAssign> findAll(HUID organizationId) {
		var taskAssignEntities = repository.queryAllByOrganizationIdAndState(
				organizationId.toUUID(), EntityState.ACTIVE);
		
		return taskAssignEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<TaskAssign> findByTaskId(HUID organizationId, HUID taskId) {
		var taskAssignEntities = repository.queryByOrganizationIdAndTaskIdAndState(
				organizationId.toUUID(), taskId.toUUID(), EntityState.ACTIVE);
		
		return taskAssignEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<TaskAssign> findByMemberId(HUID organizationId, HUID memberId) {
		var taskAssignEntities = repository.queryByOrganizationIdAndMemberIdAndState(
				organizationId.toUUID(), memberId.toUUID(), EntityState.ACTIVE);
		
		return taskAssignEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<TaskAssign> findByTaskIds(HUID organizationId, Set<HUID> taskIds) {
		var uuids = taskIds.stream()
				.map(HUID::toUUID)
				.toList();
		
		var taskAssignEntities = repository.queryByOrganizationIdAndTaskIdInAndState(
				organizationId.toUUID(), uuids, EntityState.ACTIVE);
		
		return taskAssignEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public List<TaskAssign> findByMemberIds(HUID organizationId, Set<HUID> memberIds) {
		var uuids = memberIds.stream()
				.map(HUID::toUUID)
				.toList();
		
		var taskAssignEntities = repository.queryByOrganizationIdAndMemberIdInAndState(
				organizationId.toUUID(), uuids, EntityState.ACTIVE);
		
		return taskAssignEntities.stream()
				.map(this::toDomainEntity)
				.toList();
	}

	@Override
	public void save(HUID organizationId, TaskAssign taskAssign) {
		taskAssign.ensureBelongsToOrganization(organizationId);
		
		var taskAssignEntity = toInfrastructureEntity(taskAssign);
		
		repository.save(taskAssignEntity);
	}

	@Override
	public TaskAssignEntity toInfrastructureEntity(TaskAssign domainEntity) {
		return mapper.toInfrastructureEntity(domainEntity);
	}

	@Override
	public TaskAssign toDomainEntity(TaskAssignEntity infrastructureEntity) {
		return mapper.toDomainEntity(infrastructureEntity);
	}

}
