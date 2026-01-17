package software.blacknode.backend.api.controller.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@AllArgsConstructor
@ToString
public class AuthenticateWithPasswordRequest extends BaseRequest {
	
	private String email;
	private String password;
	
}
