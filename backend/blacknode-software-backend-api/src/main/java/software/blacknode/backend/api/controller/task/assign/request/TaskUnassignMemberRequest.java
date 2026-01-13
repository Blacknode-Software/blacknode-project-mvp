package software.blacknode.backend.api.controller.task.assign.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@Builder
public class TaskUnassignMemberRequest extends BaseRequest {

	private UUID memberId;
	
}
