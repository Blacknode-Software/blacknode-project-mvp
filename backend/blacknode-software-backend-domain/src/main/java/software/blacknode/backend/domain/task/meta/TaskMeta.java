package software.blacknode.backend.domain.task.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@With
@AllArgsConstructor
public class TaskMeta {

	private String title;
	private String description;
	
}
