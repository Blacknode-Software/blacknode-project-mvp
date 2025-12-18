package software.blacknode.backend.infrastructure.entity.state;

public enum EntityState {

	ACTIVE,
	DELETED,
	;
	
	public boolean isActive() {
		return this == ACTIVE;
	}
	
	public boolean isDeleted() {
		return this == DELETED;
	}
}
