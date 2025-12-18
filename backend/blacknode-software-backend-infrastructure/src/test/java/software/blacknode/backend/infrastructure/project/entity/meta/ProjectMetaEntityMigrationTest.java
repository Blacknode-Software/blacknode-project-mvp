package software.blacknode.backend.infrastructure.project.entity.meta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import software.blacknode.backend.infrastructure.entity.version.json.VersionedEntityJson;
import software.blacknode.backend.infrastructure.entity.version.migrator.VersionedEntityMigrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectMetaEntityMigrationTest {

    @Test
    void testMigrationFromV1ToV2() {
        // Arrange
        ObjectMapper mapper = new ObjectMapper();
        // Configure mapper to access private fields directly, as V001 class might not have setters/getters
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        
        VersionedEntityMigrator migrator = new VersionedEntityMigrator(mapper);

        String name = "Test Project";
        String description = "Test Description";
        
        // Create JSON for V1 (ProjectMetaEntity_V001)
        ObjectNode content = mapper.createObjectNode();
        content.put("name", name);
        content.put("description", description);
        
        VersionedEntityJson json = new VersionedEntityJson(1, content);

        // Act
        Object result = migrator.migrate(json, ProjectMetaEntity.class);

        // Assert
        assertTrue(result instanceof ProjectMetaEntity);
        ProjectMetaEntity entity = (ProjectMetaEntity) result;
        
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals("#FFFFFF", entity.getColor()); // Default color added in upgrade
    }
}
