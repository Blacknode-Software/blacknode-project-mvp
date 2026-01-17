package software.blacknode.backend.api.controller.task.assign.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@AllArgsConstructor
@ToString
public class TaskUnassignMemberRequest extends BaseRequest {

	private UUID memberId;
	
}
