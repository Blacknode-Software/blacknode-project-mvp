package software.blacknode.backend.api.controller.invite.response;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class InviteCreateResponse extends BaseResponse<InviteCreateResponse> {

	private final UUID inviteId;
	
	private final String token;
	
}
