package software.blacknode.backend.infrastructure.entity.version.migration;

import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;

public interface MigrationEntity<UP extends VersionableEntity> extends VersionableEntity {

	public UP upgrade();
	
}
