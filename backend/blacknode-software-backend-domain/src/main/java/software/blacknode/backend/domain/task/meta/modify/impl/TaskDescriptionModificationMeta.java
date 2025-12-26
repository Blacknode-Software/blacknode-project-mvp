package software.blacknode.backend.domain.task.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

@Builder
@ToString
public class TaskDescriptionModificationMeta implements TaskModificationMeta {
	private final String description;

	public Optional<String> getDescription() {
		return Optional.ofNullable(description);
	}
}
