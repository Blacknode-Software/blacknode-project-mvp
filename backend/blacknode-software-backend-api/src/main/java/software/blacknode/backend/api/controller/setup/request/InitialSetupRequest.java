package software.blacknode.backend.api.controller.setup.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@AllArgsConstructor
public class InitialSetupRequest extends BaseRequest {

	private String organizationName;
	
	private String adminFirstName;
	private String adminLastName;
	private String adminEmail;
	private String adminPassword;
	
}
