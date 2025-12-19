package software.blacknode.backend.infrastructure.entity.mapper.annotation.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "modificationTimestamp", source = "modifiedAt", qualifiedByName = "instant2Timestamp")
public @interface ModificationMappingDomain {

}
