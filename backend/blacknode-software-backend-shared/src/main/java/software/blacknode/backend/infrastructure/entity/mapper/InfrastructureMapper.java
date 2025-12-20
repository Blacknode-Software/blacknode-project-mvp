package software.blacknode.backend.infrastructure.entity.mapper;

import java.time.Instant;
import java.util.UUID;

import org.mapstruct.Named;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.infrastructure.entity.InfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.shared.mapper.BaseMapper;

public interface InfrastructureMapper<D extends DomainEntity, E extends InfrastructureEntity> extends BaseMapper {
	
	D toDomainEntity(E infrastructureEntity);

	E toInfrastructureEntity(D domainEntity);
	
	@Named("instant2Timestamp")
	public default Timestamp instantToTimestamp(Instant instant) {
		if (instant == null) {
			return null;
		}
		return Timestamp.fromInstant(instant);
	}
	
	@Named("timestamp2Instant")
	public default Instant timestampToInstant(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return timestamp.toInstant();
	}
	
	@Named("uuid2HUID")
	public default HUID uuidToHUID(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return HUID.fromUUID(uuid);
	}
	
	@Named("huid2UUID")
	public default UUID huidToUUID(HUID huid) {
		if (huid == null) {
			return null;
		}
		return huid.toUUID();
	}
	
	@Named("getEntityState")
	public default EntityState getEntityState(D domainEntity) {
		EntityState state = EntityState.NOT_DEFINED;
		
		// TODO add logging if state is NOT_DEFINED
		
		if(domainEntity instanceof Creatable _entity) {
			if(_entity.getCreationTimestamp() != null) {
				state = EntityState.ACTIVE;
			}
		}
		
		if(domainEntity instanceof Deletable _entity) {
			if(_entity.getDeletionTimestamp() != null) {
				state = EntityState.DELETED;
			}
		}
		
		return state;
	}
}
