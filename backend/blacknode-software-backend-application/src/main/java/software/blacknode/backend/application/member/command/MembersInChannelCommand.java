package software.blacknode.backend.application.member.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class MembersInChannelCommand implements ExecutionCommand {

	@NonNull
	private final HUID channelId;
	
}
