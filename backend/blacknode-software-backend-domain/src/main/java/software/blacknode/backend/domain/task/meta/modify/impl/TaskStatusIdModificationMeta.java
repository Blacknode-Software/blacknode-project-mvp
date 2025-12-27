package software.blacknode.backend.domain.task.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

@Builder
@ToString
public class TaskStatusIdModificationMeta implements TaskModificationMeta {

    private final HUID statusId;

    public Optional<HUID> getStatusId() {
        return Optional.ofNullable(statusId);
    }

}
