package software.blacknode.backend.infrastructure.channel.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.channel.meta.ChannelMeta;
import software.blacknode.backend.infrastructure.channel.entity.ChannelEntity;
import software.blacknode.backend.infrastructure.channel.entity.meta.ChannelEntityMeta;
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

@Mapper(componentModel = "spring")
public interface ChannelEntityMapper extends InfrastructureMapper<Channel, ChannelEntity> {

	@IdMappingInfrastructure
	@OrganizationIdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	ChannelEntity toInfrastructureEntity(Channel domainEntity);

	@IdMappingDomain
	@OrganizationIdMappingDomain
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Channel toDomainEntity(ChannelEntity infrastructureEntity);

	ChannelEntityMeta map(ChannelMeta meta);
	
	ChannelMeta map(ChannelEntityMeta meta);
	
}
