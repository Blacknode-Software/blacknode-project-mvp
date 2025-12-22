package software.blacknode.backend.application.project.command;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class ProjectsBatchFetchCommand implements ExecutionCommand {

	@NonNull
	private List<HUID> projectIds;
	
}
