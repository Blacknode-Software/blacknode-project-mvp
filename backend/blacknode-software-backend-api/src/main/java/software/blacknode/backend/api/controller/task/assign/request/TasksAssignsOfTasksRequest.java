package software.blacknode.backend.api.controller.task.assign.request;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
@ToString
public class TasksAssignsOfTasksRequest extends BaseRequest {

	private List<UUID> taskIds;
	
}
