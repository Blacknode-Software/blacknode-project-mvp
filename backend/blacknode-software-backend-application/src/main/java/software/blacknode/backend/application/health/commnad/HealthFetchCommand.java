package software.blacknode.backend.application.health.commnad;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.application.command.ExecutionCommand;

@Getter
@Builder
@ToString
public class HealthFetchCommand implements ExecutionCommand {

}
