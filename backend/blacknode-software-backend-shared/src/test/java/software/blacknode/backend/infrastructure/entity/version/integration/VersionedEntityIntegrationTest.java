package software.blacknode.backend.infrastructure.entity.version.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.config.VersionedEntityConfig;
import software.blacknode.backend.infrastructure.entity.version.json.converter.VersionedEntityJsonConverter;
import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({VersionedEntityConfig.class, VersionedEntityJsonConverter.class})
@EnableJpaRepositories(basePackageClasses = TestEntityRepository.class)
@EntityScan(basePackageClasses = TestEntity.class)
class VersionedEntityIntegrationTest {

    @Autowired
    private TestEntityRepository repository;

    @Autowired
    private ObjectMapper mapper;

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

    @TestConfiguration
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class Config {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
        
        @Bean
        public VersionedEntityJsonConverter versionedEntityJsonConverter(ObjectMapper mapper) {
            VersionedEntityJsonConverter converter = new VersionedEntityJsonConverter();
            converter.init(mapper);
            return converter;
        }
    }
}