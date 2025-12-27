package software.blacknode.backend.infrastructure.project.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.ProjectMeta;
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
import software.blacknode.backend.infrastructure.project.entity.ProjectEntity;
import software.blacknode.backend.infrastructure.project.entity.meta.ProjectEntityMeta;

@Mapper(componentModel = "spring")
public interface ProjectEntityMapper extends InfrastructureMapper<Project, ProjectEntity> {
	
	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	ProjectEntity toInfrastructureEntity(Project domain);
	
	@IdMappingDomain
	@OrganizationIdMappingDomain
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Project toDomainEntity(ProjectEntity entity);
	
	ProjectEntityMeta map(ProjectMeta meta);
	
	ProjectMeta map(ProjectEntityMeta meta);
}
