package software.blacknode.backend.api.controller.task.assign.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TaskAssignResponseContent {

	private UUID id;
	
	private UUID taskId;
	private UUID memberId;
	
}
