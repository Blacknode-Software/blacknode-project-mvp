package software.blacknode.backend.api.controller.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@AllArgsConstructor
@ToString
public class AccountPatchRequest extends PatchRequest {

	private String firstName;
	private String lastName;
	
}
