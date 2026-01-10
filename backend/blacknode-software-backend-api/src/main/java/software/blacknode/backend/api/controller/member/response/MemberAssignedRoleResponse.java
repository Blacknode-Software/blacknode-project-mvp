package software.blacknode.backend.api.controller.member.response;

import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class MemberAssignedRoleResponse extends BaseResponse<MemberAssignedRoleResponse> {

	@NonNull
	private final UUID roleId;
	
}
