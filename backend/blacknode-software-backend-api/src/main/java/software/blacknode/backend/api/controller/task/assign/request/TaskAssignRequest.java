package software.blacknode.backend.api.controller.task.assign.request;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
public class TaskAssignRequest extends BaseRequest {
	
	private UUID memberId;
	
}
