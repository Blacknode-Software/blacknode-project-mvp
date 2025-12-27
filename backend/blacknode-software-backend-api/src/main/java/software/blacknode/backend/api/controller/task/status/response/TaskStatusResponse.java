package software.blacknode.backend.api.controller.task.status.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;
import software.blacknode.backend.api.controller.task.status.response.content.TaskStatusResponseContent;

@SuperBuilder
public class TaskStatusResponse extends TaskStatusResponseContent implements ResponseBySetter<TaskStatusResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
}
