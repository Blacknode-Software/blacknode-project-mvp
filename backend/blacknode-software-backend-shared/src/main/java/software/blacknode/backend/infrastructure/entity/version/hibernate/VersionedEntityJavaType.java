package software.blacknode.backend.infrastructure.entity.version.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.BasicJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.json.VersionedEntityJson;
import software.blacknode.backend.infrastructure.entity.version.migrator.VersionedEntityMigrator;
import software.blacknode.backend.infrastructure.entity.version.reslover.VersionResolver;

/**
 * Custom Java type descriptor for versioned entities.
 * This allows Hibernate to properly serialize/deserialize versioned entities to/from JSON strings.
 */
public class VersionedEntityJavaType implements BasicJavaType<VersionableEntity> {

    private static ObjectMapper mapper;
    private static VersionedEntityMigrator migrator;

    public static void init(ObjectMapper objectMapper) {
        mapper = objectMapper;
        migrator = new VersionedEntityMigrator(objectMapper);
    }

    @Override
    public Class<VersionableEntity> getJavaTypeClass() {
        return VersionableEntity.class;
    }

    @Override
    public JdbcType getRecommendedJdbcType(JdbcTypeIndicators indicators) {
        return indicators.getJdbcType(SqlTypes.VARCHAR);
    }

    @Override
    public VersionableEntity fromString(CharSequence string) {
        if (string == null) return null;
        
        try {
            JsonNode root = mapper.readTree(string.toString());
            
            // Check if it's versioned JSON
            if (root.has("version") && root.has("content") && root.has("type")) {
                VersionedEntityJson json = mapper.treeToValue(root, VersionedEntityJson.class);
                Class<?> targetType = Class.forName(json.type());
                return (VersionableEntity) migrator.migrate(json, targetType);
            }
            
            // Not versioned - can't deserialize without type info
            throw new IllegalStateException("Cannot deserialize versioned entity without type information");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to deserialize versioned entity", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> X unwrap(VersionableEntity value, Class<X> type, WrapperOptions options) {
        if (value == null) return null;
        
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        
        throw new UnsupportedOperationException("Unwrap not supported for type: " + type);
    }

    @Override
    public <X> VersionableEntity wrap(X value, WrapperOptions options) {
        if (value == null) return null;
        
        if (value instanceof String) {
            return fromString((String) value);
        }
        
        throw new UnsupportedOperationException("Wrap not supported for type: " + value.getClass());
    }

    public String toString(VersionableEntity value) {
        if (value == null) return null;
        
        try {
            Class<?> type = value.getClass();
            int version = 1;
            
            if (type.isAnnotationPresent(VersionedEntity.class)) {
                version = VersionResolver.resolve(type).latest();
            }

            VersionedEntityJson json = new VersionedEntityJson(version, type.getName(), mapper.valueToTree(value));
            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize versioned entity", e);
        }
    }
}
