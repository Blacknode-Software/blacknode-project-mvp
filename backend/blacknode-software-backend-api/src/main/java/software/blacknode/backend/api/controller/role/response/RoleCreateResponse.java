package software.blacknode.backend.api.controller.role.response;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class RoleCreateResponse extends BaseResponse<RoleCreateResponse> {

	private final UUID roleId;
	
}
