package software.blacknode.backend.application.role.command;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class RoleFetchCommand implements ExecutionCommand {

	private final HUID roleId;
	
}
