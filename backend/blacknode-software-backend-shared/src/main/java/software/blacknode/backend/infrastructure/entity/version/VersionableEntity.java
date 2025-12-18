package software.blacknode.backend.infrastructure.entity.version;

import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

public interface VersionableEntity {

	public static int getEntityVersion(Class<? extends MigrationEntity<?>> clazz) {
		String className = clazz.getSimpleName();
		
		className = className.substring(className.length() - 3);
		
		return Integer.parseInt(className);
	}
	
	public static int getLatestEntityVersion(Class<? extends VersionableEntity> clazz) {
		int latestVersion = 0;
		
		for (Class<?> innerClass : clazz.getDeclaredClasses()) {
			if (MigrationEntity.class.isAssignableFrom(innerClass)) {
				@SuppressWarnings("unchecked")
				Class<? extends MigrationEntity<?>> migrationClass = (Class<? extends MigrationEntity<?>>) innerClass;
				
				int version = getEntityVersion(migrationClass);
				
				if (version > latestVersion) {
					latestVersion = version;
				}
			}
		}
		
		return latestVersion;
	}
	
}
