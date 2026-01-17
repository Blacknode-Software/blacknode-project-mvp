package software.blacknode.backend.api.controller.setup.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.BaseRequest;

@Getter
@NoArgsConstructor
@ToString
public class InitialSetupRequest extends BaseRequest {

	private String organizationName;
	
	private String adminFirstName;
	private String adminLastName;
	private String adminEmail;
	private String adminPassword;
	
}