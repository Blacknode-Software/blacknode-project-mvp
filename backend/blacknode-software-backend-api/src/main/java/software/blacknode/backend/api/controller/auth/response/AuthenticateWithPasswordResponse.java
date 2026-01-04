package software.blacknode.backend.api.controller.auth.response;

import java.time.Instant;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.BaseResponse;

@Getter
@SuperBuilder
@ToString
public class AuthenticateWithPasswordResponse extends BaseResponse<AuthenticateWithPasswordResponse> {
	
	private String accessToken;
	private Instant accessTokenExpiresAt;
}
