package software.blacknode.backend.api.controller.invite.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
public class InvitePreClaimFetchResponse extends BaseResponse<InvitePreClaimFetchResponse> {

	private final String email;

}
