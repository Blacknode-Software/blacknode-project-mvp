package software.blacknode.backend.infrastructure.repository;

import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.infrastructure.entity.InfrastructureEntity;

public interface InfrastructureRepository<D extends DomainEntity, E extends InfrastructureEntity> {
	
	public E toInfrastructureEntity(D domainEntity);
	
	public D toDomainEntity(E infrastructureEntity);
	
}
