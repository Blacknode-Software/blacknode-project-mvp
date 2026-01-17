package software.blacknode.backend.api.controller.task.assign.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@AllArgsConstructor
@ToString
public class TasksAssignsOfMembersRequest extends BaseRequest {

	private List<UUID> memberIds;
	
}
