package software.blacknode.backend.application.project.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class ProjectDeleteCommand implements ExecutionCommand {

	@NonNull
	private HUID projectId;
	
}
