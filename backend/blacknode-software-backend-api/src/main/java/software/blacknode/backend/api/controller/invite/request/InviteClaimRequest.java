package software.blacknode.backend.api.controller.invite.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
public class InviteClaimRequest extends BaseRequest {

	private String token;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
}
