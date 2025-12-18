package software.blacknode.backend.infrastructure.entity.version.integration;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import software.blacknode.backend.infrastructure.entity.version.json.converter.VersionedEntityJsonConverter;

import java.util.UUID;

@Entity
@Table(name = "test_entity")
@Data
public class TestEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Convert(converter = VersionedEntityJsonConverter.class)
    private MyVersionedObject versionedData;
}
