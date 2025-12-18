package software.blacknode.backend.infrastructure.entity.version.json.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.json.VersionedEntityJson;
import software.blacknode.backend.infrastructure.entity.version.migrator.VersionedEntityMigrator;
import software.blacknode.backend.infrastructure.entity.version.reslover.VersionResolver;

@Converter(autoApply = true)
public class VersionedEntityJsonConverter
        implements AttributeConverter<Object, String> {

    private static ObjectMapper mapper;
    private static VersionedEntityMigrator migrator;

    @Autowired
    public void init(ObjectMapper mapper) {
        VersionedEntityJsonConverter.mapper = mapper;
        VersionedEntityJsonConverter.migrator =
                new VersionedEntityMigrator(mapper);
    }

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) return null;

        Class<?> type = attribute.getClass();
        if (!type.isAnnotationPresent(VersionedEntity.class))
            return serializePlain(attribute);

        try {
            int version = VersionResolver.resolve(type).latest();

            VersionedEntityJson json = new VersionedEntityJson(
                    version,
                    mapper.valueToTree(attribute)
            );

            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        try {
            JsonNode root = mapper.readTree(dbData);

            // fast path: not versioned JSON
            if (!root.has("version") || !root.has("content"))
                return mapper.treeToValue(root, Object.class);

            VersionedEntityJson json =
                    mapper.treeToValue(root, VersionedEntityJson.class);

            // Hibernate gives us no target type here
            throw new IllegalStateException(
                    "Versioned entities resolved in interceptor"
            );

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String serializePlain(Object attr) {
        try {
            return mapper.writeValueAsString(attr);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
