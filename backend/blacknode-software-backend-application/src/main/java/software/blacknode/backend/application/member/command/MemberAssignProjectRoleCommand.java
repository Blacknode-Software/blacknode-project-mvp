package software.blacknode.backend.application.member.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class MemberAssignProjectRoleCommand implements ExecutionCommand {

	private final HUID memberId;
	private final HUID roleId;
	
	private final HUID projectId;
	
}
