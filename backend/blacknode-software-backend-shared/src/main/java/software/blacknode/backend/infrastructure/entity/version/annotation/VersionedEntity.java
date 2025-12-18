package software.blacknode.backend.infrastructure.entity.version.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Convert;
import software.blacknode.backend.infrastructure.entity.version.hibernate.VersionedEntityJavaType;
import software.blacknode.backend.infrastructure.entity.version.json.converter.VersionedEntityJsonConverter;

/**
 * Annotation to mark a field as a versioned entity field.
 * This annotation automatically applies the versioned entity type handling
 * to convert the field to/from JSON with version information.
 * 
 * Usage:
 * <pre>
 * {@code
 * @Entity
 * public class MyEntity {
 *     @VersionedEntity
 *     private MyVersionedObject data;
 * }
 * }
 * </pre>
 */

@Target(value = { ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@JavaType(VersionedEntityJavaType.class)
@JdbcTypeCode(SqlTypes.VARCHAR)
public @interface VersionedEntity {

}
