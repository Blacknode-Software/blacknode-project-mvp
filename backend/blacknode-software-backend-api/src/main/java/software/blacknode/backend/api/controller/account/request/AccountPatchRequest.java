package software.blacknode.backend.api.controller.account.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
@ToString
public class AccountPatchRequest extends PatchRequest {

	private String firstName;
	private String lastName;
	
}
