package software.blacknode.backend.api.controller.task.assign.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BatchFetchResponse;
import software.blacknode.backend.api.controller.task.assign.response.content.TaskAssignResponseContent;

@Getter
@SuperBuilder
public class TasksAssignsBatchFetchResponse extends BatchFetchResponse<TasksAssignsBatchFetchResponse, TaskAssignResponseContent> {

}
