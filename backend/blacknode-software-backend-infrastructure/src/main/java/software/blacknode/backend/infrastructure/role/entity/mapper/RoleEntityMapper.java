package software.blacknode.backend.infrastructure.role.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.role.Role;
import software.blacknode.backend.domain.role.meta.RoleMeta;
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
import software.blacknode.backend.infrastructure.role.entity.RoleEntity;
import software.blacknode.backend.infrastructure.role.entity.meta.RoleEntityMeta;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper extends InfrastructureMapper<Role, RoleEntity> {

	@Override
	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	RoleEntity toInfrastructureEntity(Role domain);
	
	@Override
	@IdMappingDomain
	@OrganizationIdMappingDomain
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Role toDomainEntity(RoleEntity entity);
	
	RoleEntityMeta map(RoleMeta meta);
	
	RoleMeta map(RoleEntityMeta meta);
}
