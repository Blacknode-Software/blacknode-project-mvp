package software.blacknode.backend.application.channel.command;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class ChannelsBatchFetchCommand implements ExecutionCommand {

	@NonNull
	private final List<HUID> channelIds;
	
}
