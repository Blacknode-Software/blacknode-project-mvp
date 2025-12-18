package software.blacknode.backend.infrastructure.entity.version.migrator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.json.VersionedEntityJson;
import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;
import software.blacknode.backend.infrastructure.entity.version.reslover.VersionResolver;

public final class VersionedEntityMigrator {

    private final ObjectMapper mapper;

    public VersionedEntityMigrator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Object migrate(
            VersionedEntityJson json,
            Class<?> rootType
    ) {
        if (!rootType.isAnnotationPresent(VersionedEntity.class)) {
            throw new IllegalArgumentException(
                    "Type is not @VersionedEntity: " + rootType
            );
        }

        VersionResolver.VersionInfo info =
                VersionResolver.resolve(rootType);

        Class<?> versionType = info.versions().get(json.version());
        if (versionType == null) {
            throw new IllegalStateException(
                    "Unknown version " + json.version()
            );
        }

        Object current = deserialize(json.content(), versionType);

        while (current instanceof MigrationEntity<?> m) {
            current = m.upgrade();
        }

        return current;
    }

    private Object deserialize(JsonNode node, Class<?> type) {
        try {
            return mapper.treeToValue(node, type);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

