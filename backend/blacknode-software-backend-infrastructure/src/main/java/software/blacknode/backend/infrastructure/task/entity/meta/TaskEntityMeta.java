package software.blacknode.backend.infrastructure.task.entity.meta;

import java.time.Instant;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@VersionedEntity
@ToString
public class TaskEntityMeta implements VersionableEntity {

	@NotNull private String title;
	@NotNull private String description;
	
	@Nullable private Integer priority;
	
	@Nullable private Instant beginAt;
	@Nullable private Instant endAt;
	
}
