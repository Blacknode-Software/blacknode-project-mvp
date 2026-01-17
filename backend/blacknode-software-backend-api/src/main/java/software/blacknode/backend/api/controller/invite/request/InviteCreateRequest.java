package software.blacknode.backend.api.controller.invite.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@AllArgsConstructor
@ToString
public class InviteCreateRequest extends BaseRequest{

	private String email;
	
}
