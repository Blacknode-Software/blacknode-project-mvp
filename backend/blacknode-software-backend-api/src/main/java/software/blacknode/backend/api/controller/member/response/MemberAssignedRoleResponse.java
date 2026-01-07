package software.blacknode.backend.api.controller.member.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class MemberAssignedRoleResponse extends BaseResponse<MemberAssignedRoleResponse> {

	@NonNull
	private final HUID roleId;
	
}
