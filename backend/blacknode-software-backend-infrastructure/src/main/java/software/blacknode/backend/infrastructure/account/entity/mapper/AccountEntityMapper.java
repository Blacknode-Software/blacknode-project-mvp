package software.blacknode.backend.infrastructure.account.entity.mapper;

import org.mapstruct.Mapper;

import software.blacknode.backend.domain.account.Account;
import software.blacknode.backend.domain.account.meta.AccountMeta;
import software.blacknode.backend.infrastructure.account.entity.AccountEntity;
import software.blacknode.backend.infrastructure.account.entity.meta.AccountEntityMeta;
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
public interface AccountEntityMapper extends InfrastructureMapper<Account, AccountEntity> {

	@Override
	@IdMappingInfrastructure
	
	@CreationMappingInfrastructure
	@ModificationMappingInfrastructure
	@DeletionMappingInfrastructure
	
	@StateMappingInfrastructure
	AccountEntity toInfrastructureEntity(Account domain);
	
	@Override
	@IdMappingDomain
	
	@CreationMappingDomain
	@ModificationMappingDomain
	@DeletionMappingDomain
	Account toDomainEntity(AccountEntity entity);
	
	AccountEntityMeta map(AccountMeta meta);
	
	AccountMeta map(AccountEntityMeta meta);
	
}
