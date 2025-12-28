package software.blacknode.backend.domain.task.meta;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;

@Getter
@With
@Builder
public class TaskMeta {

	@NotBlank
	@Size(min = 3, max = 100)
	private String title;
	
	@NotNull
	@Size(max = 1024)
	private String description;
	
	private Optional<Integer> priority;
	
	private Optional<Timestamp> beginAt;
	private Optional<Timestamp> endAt;

}
