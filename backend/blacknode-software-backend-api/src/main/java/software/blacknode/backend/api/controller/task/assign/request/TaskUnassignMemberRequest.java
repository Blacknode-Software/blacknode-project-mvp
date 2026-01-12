package software.blacknode.backend.api.controller.task.assign.request;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@Builder
public class TaskUnassignMemberRequest extends BaseRequest {

	private HUID memberId;
	
}
