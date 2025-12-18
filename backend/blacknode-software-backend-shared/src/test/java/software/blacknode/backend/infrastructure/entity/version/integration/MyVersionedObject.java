package software.blacknode.backend.infrastructure.entity.version.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@VersionedEntity
public class MyVersionedObject implements VersionableEntity {
	
    private String name;
    private int value;
    private String description;

    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyVersionedObject_V002 implements MigrationEntity<MyVersionedObject> {
    	private String name;
        private int value;

        @Override
        public MyVersionedObject upgrade() {
        	return MyVersionedObject.builder()
					.name(name)
					.value(value)
					.description("Default description")
					.build();
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyVersionedObject_V001 implements MigrationEntity<MyVersionedObject_V002> {
        private String oldName;
        private int value;

        @Override
        public MyVersionedObject_V002 upgrade() {
            return MyVersionedObject_V002.builder()
                    .name(oldName + "_Migrated")
                    .value(value)
                    .build();
        }
    }
}
