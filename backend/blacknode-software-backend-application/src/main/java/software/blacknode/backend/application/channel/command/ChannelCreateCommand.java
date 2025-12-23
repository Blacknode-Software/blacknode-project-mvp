package software.blacknode.backend.application.channel.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class ChannelCreateCommand implements ExecutionCommand {

	private final String name;
	private final String description;
	private final String color;
	
	private final HUID projectId;
}
