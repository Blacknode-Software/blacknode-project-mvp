package software.blacknode.backend.application.channel.command;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class ChannelCreationCommand implements ExecutionCommand {

	private final String name;
	private final String description;
	private final String color;
	
	private final HUID projectId;
}
