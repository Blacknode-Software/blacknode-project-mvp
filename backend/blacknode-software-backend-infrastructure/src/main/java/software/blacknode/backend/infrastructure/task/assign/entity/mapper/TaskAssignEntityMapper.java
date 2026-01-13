package software.blacknode.backend.infrastructure.task.assign.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.task.assign.TaskAssign;
import software.blacknode.backend.domain.task.assign.meta.TaskAssignMeta;
import software.blacknode.backend.infrastructure.entity.mapper.InfrastructureMapper;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.CreationMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.DeletionMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.IdMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.OrganizationIdMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.CreationMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.DeletionMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.IdMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.OrganizationIdMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.StateMappingInfrastructure;
import software.blacknode.backend.infrastructure.task.assign.entity.TaskAssignEntity;
import software.blacknode.backend.infrastructure.task.assign.entity.meta.TaskAssignEntityMeta;

@Mapper(componentModel = "spring")
public interface TaskAssignEntityMapper extends InfrastructureMapper<TaskAssign, TaskAssignEntity> {

	@Override
	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	TaskAssignEntity toInfrastructureEntity(TaskAssign domain);
	
	@Override
	@IdMappingDomain
	@OrganizationIdMappingDomain
	
	@CreationMappingDomain
	@DeletionMappingDomain
	TaskAssign toDomainEntity(TaskAssignEntity entity);
	
	TaskAssignEntityMeta map(TaskAssignMeta meta);
	
	TaskAssignMeta map(TaskAssignEntityMeta meta);
}
