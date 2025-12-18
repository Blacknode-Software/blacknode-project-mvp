package software.blacknode.backend.api.controller.task.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BatchResponse;

@Getter
@SuperBuilder
public class TaskBatchResponse extends BatchResponse<TaskBatchResponse, TaskResponse> {
	
}
