package software.blacknode.backend.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@Builder
public class MemberAssignRoleRequest extends BaseRequest {

	private final HUID roleId;
	
}
