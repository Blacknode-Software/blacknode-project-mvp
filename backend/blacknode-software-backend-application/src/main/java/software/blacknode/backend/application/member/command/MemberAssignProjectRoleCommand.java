package software.blacknode.backend.application.member.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class MemberAssignProjectRoleCommand implements ExecutionCommand {

	@NonNull
	private final HUID memberId;
	
	@NonNull
	private final HUID roleId;
	
	@NonNull
	private final HUID projectId;
	
}
