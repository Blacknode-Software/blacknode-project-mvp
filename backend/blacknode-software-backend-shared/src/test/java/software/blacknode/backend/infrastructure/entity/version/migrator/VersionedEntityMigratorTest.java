package software.blacknode.backend.infrastructure.entity.version.migrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.json.VersionedEntityJson;
import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionedEntityMigratorTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final VersionedEntityMigrator migrator = new VersionedEntityMigrator(mapper);

    @Test
    void testMigrationFromV001() {
        // Prepare V001 data
        ObjectNode content = mapper.createObjectNode();
        content.put("oldName", "OldValue");
        content.put("description", "Description");

        // Version 1 corresponds to MyTestEntity_V001
        VersionedEntityJson json = new VersionedEntityJson(1, content);

        // Migrate
        Object result = migrator.migrate(json, MyTestEntity.class);

        // Verify
        assertTrue(result instanceof MyTestEntity);
        MyTestEntity entity = (MyTestEntity) result;
        assertEquals("OldValue_Upgraded", entity.getNewName());
        assertEquals("Description", entity.getDescription());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @VersionedEntity
    public static class MyTestEntity implements VersionableEntity {
        private String newName;
        private String description;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MyTestEntity_V001 implements MigrationEntity<MyTestEntity> {
            private String oldName;
            private String description;

            @Override
            public MyTestEntity upgrade() {
                return MyTestEntity.builder()
                        .newName(oldName + "_Upgraded")
                        .description(description)
                        .build();
            }
        }
    }
}
