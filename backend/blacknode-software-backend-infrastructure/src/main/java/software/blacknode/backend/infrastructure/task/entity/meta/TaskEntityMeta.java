package software.blacknode.backend.infrastructure.task.entity.meta;

import java.time.Instant;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@VersionedEntity
public class TaskEntityMeta implements VersionableEntity {

	@NonNull private String title;
	@NonNull private String description;
	
	@Nullable private Integer priority;
	
	@Nullable private Instant beginAt;
	@Nullable private Instant endAt;
	
}
