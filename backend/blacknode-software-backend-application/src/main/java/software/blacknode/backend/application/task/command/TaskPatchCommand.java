package software.blacknode.backend.application.task.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.application.command.impl.PatchExecutionCommand;

@Getter
@SuperBuilder
@ToString
public class TaskPatchCommand extends PatchExecutionCommand {
	
	private final String title;
	private final String description;

	private final Integer priority;
	
	private final Timestamp beginAt;
	private final Timestamp endAt;

	private final HUID statusId;
	
	@NonNull
	private final HUID id;
}
