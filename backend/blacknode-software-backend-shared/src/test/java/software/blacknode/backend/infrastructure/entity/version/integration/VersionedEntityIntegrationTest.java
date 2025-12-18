package software.blacknode.backend.infrastructure.entity.version.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.blacknode.backend.infrastructure.entity.version.config.VersionedEntityConfig;
import software.blacknode.backend.infrastructure.entity.version.json.converter.VersionedEntityJsonConverter;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({VersionedEntityConfig.class, VersionedEntityJsonConverter.class})
@EnableJpaRepositories(basePackageClasses = TestEntityRepository.class)
@EntityScan(basePackageClasses = TestEntity.class)
class VersionedEntityIntegrationTest {

    @Autowired
    private TestEntityRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testSaveAndLoadVersionedEntity() {
        // Create a versioned object
        MyVersionedObject versionedObject = MyVersionedObject.builder()
                .name("TestName")
                .value(123)
                .build();

        // Create the entity that holds the versioned object
        TestEntity entity = new TestEntity();
        entity.setVersionedData(versionedObject);

        // Save to DB
        TestEntity saved = repository.save(entity);
        assertNotNull(saved.getId());

        // Clear persistence context to force reload from DB
        repository.flush();
        
        // Load from DB
        TestEntity loaded = repository.findById(saved.getId()).orElseThrow();
        
        // Verify
        assertNotNull(loaded.getVersionedData());
        assertEquals("TestName", loaded.getVersionedData().getName());
        assertEquals(123, loaded.getVersionedData().getValue());
    }

    @Test
    void testMigrationFromV1() {
        UUID id = UUID.randomUUID();
        String oldJson = """
            {
                "version": 1,
                "type": "software.blacknode.backend.infrastructure.entity.version.integration.MyVersionedObject",
                "content": {
                    "oldName": "OldValue",
                    "value": 456
                }
            }
            """;

        jdbcTemplate.update("INSERT INTO test_entity (id, versioned_data) VALUES (?, ?)", id, oldJson);

        // Load from DB
        TestEntity loaded = repository.findById(id).orElseThrow();

        // Verify migration
        assertNotNull(loaded.getVersionedData());
        assertEquals("OldValue_Migrated", loaded.getVersionedData().getName());
        assertEquals(456, loaded.getVersionedData().getValue());
    }

    @TestConfiguration
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class Config {
        
        @Bean
        public VersionedEntityJsonConverter versionedEntityJsonConverter(ObjectMapper mapper) {
            VersionedEntityJsonConverter converter = new VersionedEntityJsonConverter();
            converter.init(mapper);
            return converter;
        }
    }
}