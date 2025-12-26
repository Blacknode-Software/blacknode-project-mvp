package software.blacknode.backend.infrastructure.task.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.task.Task;
import software.blacknode.backend.domain.task.meta.TaskMeta;
import software.blacknode.backend.infrastructure.entity.mapper.InfrastructureMapper;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.CreationMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.DeletionMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.IdMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.ModificationMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.OrganizationIdMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.CreationMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.DeletionMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.IdMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.ModificationMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.OrganizationIdMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.StateMappingInfrastructure;
import software.blacknode.backend.infrastructure.task.entity.TaskEntity;
import software.blacknode.backend.infrastructure.task.entity.meta.TaskEntityMeta;

@Mapper(componentModel = "spring")
public interface TaskEntityMapper extends InfrastructureMapper<Task, TaskEntity> {

	
	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	TaskEntity toInfrastructureEntity(Task domain);
	
	@IdMappingDomain
	@OrganizationIdMappingDomain
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Task toDomainEntity(TaskEntity entity);
	
	TaskEntityMeta map(TaskMeta meta);
	
	TaskMeta map(TaskEntityMeta meta);
}
