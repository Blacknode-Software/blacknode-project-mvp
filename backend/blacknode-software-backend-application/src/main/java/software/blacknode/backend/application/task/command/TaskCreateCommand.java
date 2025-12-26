package software.blacknode.backend.application.task.command;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class TaskCreateCommand implements ExecutionCommand {

	private final String title;
	private final String description;
	
	private final Integer priority;
	private final Timestamp beginAtTimestamp;
	private final Timestamp endAtTimestamp;
	
	private final HUID statusId;
	
	private final HUID channelId;
	
}
