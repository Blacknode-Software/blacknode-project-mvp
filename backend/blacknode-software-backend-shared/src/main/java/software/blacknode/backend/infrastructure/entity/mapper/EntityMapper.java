package software.blacknode.backend.infrastructure.entity.mapper;

import java.time.Instant;
import java.util.UUID;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.infrastructure.entity.InfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;

public interface EntityMapper<D extends DomainEntity, E extends InfrastructureEntity> {
	
	D toDomainEntity(E infrastructureEntity);

	E toInfrastructureEntity(D domainEntity);
	
	public default Timestamp instantToTimestamp(Instant instant) {
		if (instant == null) {
			return null;
		}
		return Timestamp.fromInstant(instant);
	}
	
	public default Instant timestampToInstant(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return timestamp.toInstant();
	}
	
	public default HUID uuidToHUID(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return HUID.fromUUID(uuid);
	}
	
	public default UUID huidToUUID(HUID huid) {
		if (huid == null) {
			return null;
		}
		return huid.toUUID();
	}
	
	public default EntityState getEntityState(D domainEntity) {
		EntityState state = EntityState.ACTIVE;
		
		if(domainEntity instanceof Creatable _entity) {
			if(_entity.getCreationTimestamp() == null) {
				throw new BlacknodeException("Creation timestamp is null for entity: " + domainEntity.getClass().getSimpleName());
			}
		}
		if(domainEntity instanceof Deletable _entity) {
			return EntityState.ACTIVE;
		}
	}
}
