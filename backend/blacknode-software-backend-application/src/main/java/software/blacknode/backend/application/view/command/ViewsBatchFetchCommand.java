package software.blacknode.backend.application.view.command;

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
public class ViewsBatchFetchCommand implements ExecutionCommand {

	@NonNull
	private final List<HUID> viewIds;
	
}
