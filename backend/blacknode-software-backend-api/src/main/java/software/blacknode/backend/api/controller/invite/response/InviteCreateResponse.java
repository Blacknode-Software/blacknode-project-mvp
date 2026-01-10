package software.blacknode.backend.api.controller.invite.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class InviteCreateResponse extends BaseResponse<InviteCreateResponse> {

	private final HUID inviteId;
	
	private final String token;
	
}
