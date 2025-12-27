package software.blacknode.backend.domain.task.meta;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;

@Getter
@With
@Builder
public class TaskMeta {

	private String title;
	private String description;
	
	private Optional<Integer> priority;
	
	private Optional<Timestamp> beginAt;
	private Optional<Timestamp> endAt;

}
