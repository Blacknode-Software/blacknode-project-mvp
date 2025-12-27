package software.blacknode.backend.api.controller.task.status.response;

import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BatchResponse;
import software.blacknode.backend.api.controller.task.status.response.content.TaskStatusResponseContent;

@SuperBuilder
public class TaskStatusBatchFetchResponse extends BatchResponse<TaskStatusBatchFetchResponse, TaskStatusResponseContent> {

}
