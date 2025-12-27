package software.blacknode.backend.api.controller.task.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;
import software.blacknode.backend.api.controller.task.response.content.TaskResponseContent;

@SuperBuilder
public class TaskResponse extends TaskResponseContent implements ResponseBySetter<TaskResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}
