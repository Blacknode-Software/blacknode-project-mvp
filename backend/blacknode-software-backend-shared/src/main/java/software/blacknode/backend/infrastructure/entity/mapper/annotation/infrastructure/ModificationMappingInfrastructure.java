package software.blacknode.backend.infrastructure.entity.mapper.annotation.infrastructure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "modifiedAt", source = "modificationTimestamp")
public @interface ModificationMappingInfrastructure {

}
