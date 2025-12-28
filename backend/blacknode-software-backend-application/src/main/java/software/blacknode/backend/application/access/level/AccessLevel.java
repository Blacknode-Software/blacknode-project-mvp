package software.blacknode.backend.application.access.level;

public enum AccessLevel {
	NONE,
	READ,
	WRITE,
	MANAGE,
	
	;
	
	public boolean atLeast(AccessLevel other) {
		return this.ordinal() >= other.ordinal();
	}

	public boolean hasReadAccess() {
		return this.atLeast(READ);
	}
	
	public boolean hasWriteAccess() {
		return this.atLeast(WRITE);
	}
	
	public boolean hasManageAccess() {
		return this.atLeast(MANAGE);
	}
}