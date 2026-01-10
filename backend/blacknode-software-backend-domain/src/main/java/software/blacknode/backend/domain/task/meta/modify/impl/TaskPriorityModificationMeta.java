package software.blacknode.backend.domain.task.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

@Builder
@ToString
public class TaskPriorityModificationMeta implements TaskModificationMeta {

    private final Integer priority;

    @Override
    public boolean isPrioritySet() {
		return true;
	}
    
    @Override
    public Optional<Integer> getPriority() {
        return Optional.ofNullable(priority);
    }

}
