package software.blacknode.backend.infrastructure.project.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.domain.project.Project;
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

@Mapper(componentModel = "spring")
public interface ProjectEntityMapper extends InfrastructureMapper<Project, ProjectEntity> {
	
	@Override
	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@Mapping(target = "meta.name", source = "meta.name")
	@Mapping(target = "meta.description", source = "meta.description")
	@Mapping(target = "meta.color", source = "meta.color")
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	ProjectEntity toInfrastructureEntity(Project domain);
	
	@Override
	@IdMappingDomain
	@OrganizationIdMappingDomain
	
    @Mapping(target = "meta.name", source = "meta.name")
    @Mapping(target = "meta.description", source = "meta.description")
    @Mapping(target = "meta.color", source = "meta.color")
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Project toDomainEntity(ProjectEntity entity);
}
