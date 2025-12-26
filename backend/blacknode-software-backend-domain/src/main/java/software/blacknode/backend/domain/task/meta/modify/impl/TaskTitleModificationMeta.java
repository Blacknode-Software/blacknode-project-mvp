package software.blacknode.backend.domain.task.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

@Builder
@ToString
public class TaskTitleModificationMeta implements TaskModificationMeta {
	
	private final String title;

	@Override
	public Optional<String> getTitle() {
		return Optional.ofNullable(title);
	}
	
}
