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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyVersionedObject_V001 implements MigrationEntity<MyVersionedObject> {
        private String oldName;
        private int value;

        @Override
        public MyVersionedObject upgrade() {
            return MyVersionedObject.builder()
                    .name(oldName + "_Migrated")
                    .value(value)
                    .build();
        }
    }
}
