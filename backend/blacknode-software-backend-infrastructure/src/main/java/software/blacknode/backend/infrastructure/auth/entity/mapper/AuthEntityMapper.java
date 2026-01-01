package software.blacknode.backend.infrastructure.auth.entity.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.blacknode.backend.domain.auth.Auth;
import software.blacknode.backend.domain.auth.meta.AuthMeta;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;
import software.blacknode.backend.domain.auth.method.converter.serializer.AuthMethodSerializer;
import software.blacknode.backend.domain.auth.method.type.impl.BaseAuthMethodType;
import software.blacknode.backend.infrastructure.auth.entity.AuthEntity;
import software.blacknode.backend.infrastructure.auth.entity.meta.AuthEntityMeta;
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

@Mapper(componentModel = "spring")
public abstract class AuthEntityMapper implements InfrastructureMapper<Auth, AuthEntity> {
	
	@Autowired
	private AuthMethodSerializer serializer;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	@IdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	public abstract AuthEntity toInfrastructureEntity(Auth domainEntity);
	
	@Override
	@IdMappingDomain
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	public abstract  Auth toDomainEntity(AuthEntity infrastructureEntity);
	
	public abstract AuthEntityMeta map(AuthMeta meta);
	
	public abstract AuthMeta map(AuthEntityMeta meta);
	
	public AuthMethodSerializedModel map(AuthMethod method) {
		return serializer.serialize(method);
	}
	
	public AuthMethod map(AuthMethodSerializedModel data) {
		var typeId = data.getId();
		
		var type = BaseAuthMethodType.fromId(typeId);

		return type.getDeserializer().deserialize(data);
	}
	
}
