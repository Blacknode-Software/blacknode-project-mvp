package software.blacknode.backend.domain.task.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.task.meta.create.TaskCreationMeta;

@Builder
@Getter
public class TaskDefaultCreationMeta implements TaskCreationMeta {

	private final String title;
	private final String description;
	private final Integer priority;
	
	private final Timestamp beginAtTimestamp;
	private final Timestamp endAtTimestamp;
	
	private final HUID statusId;
	
	private final HUID ownerMemberId;
	private final HUID channelId;
	
	public Optional<String> getTitle() { 
		return Optional.ofNullable(title); 
	}
	
	public Optional<String> getDescription() { 
		return Optional.ofNullable(description); 
	}
	
	public Optional<Integer> getPriority() { 
		return Optional.ofNullable(priority); 
	}
	
	public Optional<Timestamp> getBeginAtTimestamp() { 
		return Optional.ofNullable(beginAtTimestamp);
	}
	
	public Optional<Timestamp> getEndAtTimestamp() { 
		return Optional.ofNullable(endAtTimestamp); 
	}
	
	public Optional<HUID> getStatusId() { 
		return Optional.ofNullable(statusId); 
	}
}
