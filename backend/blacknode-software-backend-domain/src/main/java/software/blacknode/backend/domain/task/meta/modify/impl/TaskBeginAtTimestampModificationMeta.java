package software.blacknode.backend.domain.task.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.ToString;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

@Builder
@ToString
public class TaskBeginAtTimestampModificationMeta implements TaskModificationMeta {

    private final Timestamp beginAt;

    public Optional<Timestamp> getBeginAtTimestamp() {
        return Optional.ofNullable(beginAt);
    }

}
