package software.blacknode.backend.api.controller.channel.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "channel.id"),
	@Mapping(target = "name", source = "channel.meta.name"),
	@Mapping(target = "description", source = "channel.meta.description"),
	@Mapping(target = "color", source = "channel.meta.color"),
})
public @interface ChannelResponseContentMapping {

}
