package software.blacknode.backend.infrastructure.entity;

import java.util.UUID;

import software.blacknode.backend.infrastructure.entity.state.EntityState;

public interface InfrastructureEntity {

	public UUID getId();
	
	public EntityState getState();
	
}
