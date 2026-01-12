package software.blacknode.backend.application.profile.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
public class ProfileFetchCommand implements ExecutionCommand {

	@NonNull
	private final HUID memberId;
	
}
