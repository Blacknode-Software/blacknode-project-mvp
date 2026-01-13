package software.blacknode.backend.api.controller.task.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BatchFetchResponse;
import software.blacknode.backend.api.controller.task.response.content.TaskResponseContent;

@Getter
@SuperBuilder
public class TasksBatchFetchResponse extends BatchFetchResponse<TasksBatchFetchResponse, TaskResponseContent> {
	
}
