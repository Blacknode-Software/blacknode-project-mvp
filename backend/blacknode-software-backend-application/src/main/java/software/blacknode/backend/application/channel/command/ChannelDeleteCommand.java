package software.blacknode.backend.application.channel.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class ChannelDeleteCommand implements ExecutionCommand {

	@NonNull
	private HUID channelId;
	
}
