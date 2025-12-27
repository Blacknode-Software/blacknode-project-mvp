package software.blacknode.backend.application.project.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class ProjectFetchCommand implements ExecutionCommand {

	@NonNull
	private final HUID projectId;
	
}
