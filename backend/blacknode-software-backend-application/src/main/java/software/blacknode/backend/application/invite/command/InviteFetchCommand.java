package software.blacknode.backend.application.invite.command;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class InviteFetchCommand implements ExecutionCommand{

	private HUID id;
	
}
