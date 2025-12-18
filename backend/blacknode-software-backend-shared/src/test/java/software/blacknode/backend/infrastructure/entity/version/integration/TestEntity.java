package software.blacknode.backend.infrastructure.entity.version.integration;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Entity
@Table(name = "test_entity")
@Data
public class TestEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @VersionedEntity
    private MyVersionedObject versionedData;
}
