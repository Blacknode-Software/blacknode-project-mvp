package software.blacknode.backend.domain.task.meta.modify.impl;

import lombok.Builder;
import lombok.ToString;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.task.meta.modify.TaskModificationMeta;

import java.util.Optional;

@Builder
@ToString
public class TaskEndAtTimestampModificationMeta implements TaskModificationMeta {

    private final Timestamp endAt;

    @Override
    public boolean isEndAtTimestampSet() {
    	return true;
    }
    
    @Override
    public Optional<Timestamp> getEndAtTimestamp() {
        return Optional.ofNullable(endAt);
    }

}
