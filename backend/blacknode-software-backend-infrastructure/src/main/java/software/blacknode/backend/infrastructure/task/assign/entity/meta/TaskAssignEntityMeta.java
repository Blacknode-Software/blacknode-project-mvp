package software.blacknode.backend.infrastructure.task.assign.entity.meta;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@VersionedEntity
@ToString
public class TaskAssignEntityMeta implements VersionableEntity {

	@Nullable private HUID assignerId;
	
}
