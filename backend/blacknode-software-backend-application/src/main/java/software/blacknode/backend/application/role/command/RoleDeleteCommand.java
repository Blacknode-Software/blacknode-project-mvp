package software.blacknode.backend.application.role.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Builder
@Getter
@ToString
public class RoleDeleteCommand implements ExecutionCommand {

	@NonNull
	private final HUID roleId;
	
}
