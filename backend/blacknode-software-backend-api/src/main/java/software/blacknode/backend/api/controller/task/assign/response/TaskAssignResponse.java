package software.blacknode.backend.api.controller.task.assign.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;
import software.blacknode.backend.api.controller.task.assign.response.content.TaskAssignResponseContent;

@Getter
@SuperBuilder
public class TaskAssignResponse extends TaskAssignResponseContent implements ResponseBySetter<TaskAssignResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
