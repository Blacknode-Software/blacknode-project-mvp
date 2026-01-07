package software.blacknode.backend.infrastructure.member.association.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.MemberAssociationMeta;
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
import software.blacknode.backend.infrastructure.member.association.entity.MemberAssociationEntity;
import software.blacknode.backend.infrastructure.member.association.entity.meta.MemberAssociationEntityMeta;

@Mapper(componentModel = "spring")
public interface MemberAssociationEntityMapper extends InfrastructureMapper<MemberAssociation, MemberAssociationEntity> {

	@Override
	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	MemberAssociationEntity toInfrastructureEntity(MemberAssociation domain);
	
	@Override
	@IdMappingDomain
	@OrganizationIdMappingDomain
	
	@CreationMappingDomain
	@DeletionMappingDomain
	MemberAssociation toDomainEntity(MemberAssociationEntity entity);
	
	MemberAssociationEntityMeta map(MemberAssociationMeta meta);
	
	MemberAssociationMeta map(MemberAssociationEntityMeta meta);
	
}
