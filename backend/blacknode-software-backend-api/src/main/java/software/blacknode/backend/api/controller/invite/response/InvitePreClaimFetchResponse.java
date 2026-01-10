package software.blacknode.backend.api.controller.invite.response;

import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@SuperBuilder
public class InvitePreClaimFetchResponse extends BaseResponse<InvitePreClaimFetchResponse> {

	private final String email;

}
