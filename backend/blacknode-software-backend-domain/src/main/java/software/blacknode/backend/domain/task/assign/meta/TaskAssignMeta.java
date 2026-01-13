package software.blacknode.backend.domain.task.assign.meta;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import me.hinsinger.hinz.common.huid.HUID;

@Getter
@With
@Builder
public class TaskAssignMeta {

	private Optional<HUID> assignerId;
	
}
