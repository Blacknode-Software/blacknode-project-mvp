package software.blacknode.backend.api.controller.task.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;
import software.blacknode.backend.api.controller.task.response.content.TaskResponseContent;

@Getter
@SuperBuilder
public class TaskPatchResponse extends TaskResponseContent implements ResponseBySetter<TaskPatchResponse> {
	
	@Getter @Setter private Status status;
	@Getter @Setter private String message;

}
