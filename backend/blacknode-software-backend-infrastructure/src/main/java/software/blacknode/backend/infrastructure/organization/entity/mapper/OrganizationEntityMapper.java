package software.blacknode.backend.infrastructure.organization.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.infrastructure.entity.mapper.InfrastructureMapper;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.CreationMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.DeletionMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.IdMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.domain.ModificationMappingDomain;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.CreationMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.DeletionMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.IdMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.ModificationMappingInfrastructure;
import software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure.StateMappingInfrastructure;
import software.blacknode.backend.infrastructure.organization.entity.OrganizationEntity;

@Mapper(componentModel = "spring")
public interface OrganizationEntityMapper extends InfrastructureMapper<Organization, OrganizationEntity> {

	@Override
	@IdMappingInfrastructure
	
	@Mapping(target = "meta.name", source = "meta.name")
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	OrganizationEntity toInfrastructureEntity(Organization domainEntity);
	
	
	@IdMappingDomain
	
	@Mapping(target = "meta.name", source = "meta.name")
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Organization toDomainEntity(OrganizationEntity infrastructureEntity);
	
}
