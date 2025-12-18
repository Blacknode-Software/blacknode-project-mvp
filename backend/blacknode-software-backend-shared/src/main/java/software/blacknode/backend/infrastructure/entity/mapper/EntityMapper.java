package software.blacknode.backend.infrastructure.entity.mapper;

import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.infrastructure.entity.InfrastructureEntity;

public interface EntityMapper<D extends DomainEntity, E extends InfrastructureEntity> {
	
	D toDomainEntity(E infrastructureEntity);

	E toInfrastructureEntity(D domainEntity);
	
}
