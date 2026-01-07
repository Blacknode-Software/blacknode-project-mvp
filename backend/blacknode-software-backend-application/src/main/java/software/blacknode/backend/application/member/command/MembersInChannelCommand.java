package software.blacknode.backend.application.member.command;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class MembersInChannelCommand implements ExecutionCommand {

	private final HUID channelId;
	
}
