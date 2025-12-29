package software.blacknode.backend.infrastructure.channel.entity.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@VersionedEntity
@ToString
public class ChannelEntityMeta implements VersionableEntity {

	@NonNull private String name;
	@NonNull private String description;
	@NonNull private String color;
	
}
