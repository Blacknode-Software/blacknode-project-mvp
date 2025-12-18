package software.blacknode.backend.infrastructure.organization.related.repository;

import java.util.Optional;
import java.util.UUID;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.infrastructure.organization.related.OrganizationRelatedEntity;
import software.blacknode.backend.infrastructure.repository.InfrastructureRepository;

public interface OrganizationRelatedEntityRepository<D extends DomainEntity, E extends OrganizationRelatedEntity> extends InfrastructureRepository<D, E> {

	public default boolean belongsToOrganization(E entity, HUID organizationId) {
		return belongsToOrganization(entity, organizationId.toUUID());
	}
	
	public default boolean belongsToOrganization(E entity, UUID organizationId) {
		return entity.getOrganizationId().equals(organizationId);
	}
	
	public default Optional<E> validateBelongsToOrganization(Optional<E> entityOpt, HUID organizationId) {
		return entityOpt.filter(entity -> belongsToOrganization(entity, organizationId));
	}
	
}
