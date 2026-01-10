package software.blacknode.backend.api.controller.invite.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
public class InviteCreateRequest extends BaseRequest{

	private String email;
	
}
